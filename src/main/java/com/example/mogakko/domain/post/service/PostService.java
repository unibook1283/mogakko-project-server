package com.example.mogakko.domain.post.service;

import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.group.domain.GroupUser;
import com.example.mogakko.domain.group.exception.GroupNotFoundException;
import com.example.mogakko.domain.group.repository.GroupRepository;
import com.example.mogakko.domain.group.repository.GroupUserRepository;
import com.example.mogakko.domain.post.domain.*;
import com.example.mogakko.domain.post.enums.Type;
import com.example.mogakko.domain.post.dto.PostRequestDTO;
import com.example.mogakko.domain.post.dto.PostResponseDTO;
import com.example.mogakko.domain.post.exception.IsNotAuthorOfPostException;
import com.example.mogakko.domain.post.exception.NotValidPostTypeException;
import com.example.mogakko.domain.post.exception.PostNotFoundException;
import com.example.mogakko.domain.post.repository.PostRepository;
import com.example.mogakko.domain.post.service.values.PostLanguageService;
import com.example.mogakko.domain.post.service.values.PostLocationService;
import com.example.mogakko.domain.post.service.values.PostOccupationService;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.exception.UserNotFoundException;
import com.example.mogakko.domain.user.repository.UserRepository;
import com.example.mogakko.domain.values.dto.LanguageDTO;
import com.example.mogakko.domain.values.dto.LocationDTO;
import com.example.mogakko.domain.values.dto.OccupationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final GroupUserRepository groupUserRepository;
    private final PostLanguageService postLanguageService;
    private final PostLocationService postLocationService;
    private final PostOccupationService postOccupationService;

    @Transactional
    public PostResponseDTO savePost(PostRequestDTO postRequestDTO) {    // languages, locations, occupations는 여기서 말고 values service에서

        User user = userRepository.findById(postRequestDTO.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Post post = postRequestDTO.toEntity(user);
        Post savePost = postRepository.save(post);

        PostResponseDTO postResponseDTO = new PostResponseDTO(savePost);

        Type type = postRequestDTO.getType();
        if (type == Type.PROJECT || type == Type.MOGAKKO) {
            GroupUser groupUser = new GroupUser();
            groupUser.setUser(user);
            groupUser.setIsMaster(true);
            GroupUser saveGroupUser = groupUserRepository.save(groupUser);

            Group group = Group.createGroup(saveGroupUser);
            Group saveGroup = groupRepository.save(group);
            savePost.setGroup(saveGroup);

            postResponseDTO.setGroupId(saveGroup.getId());
            postResponseDTO.setGroupStatus(saveGroup.getGroupStatus());

            savePostValues(postRequestDTO, postResponseDTO);
        }
        else if (type == Type.STUDY) {
            Group group = groupRepository.findById(postRequestDTO.getGroupId())
                    .orElseThrow(GroupNotFoundException::new);

            group.addStudyPost((Study)savePost);
            postResponseDTO.setGroupId(group.getId());
            postResponseDTO.setGroupStatus(group.getGroupStatus());
        }

        return postResponseDTO;
    }

    private void savePostValues(PostRequestDTO postRequestDTO, PostResponseDTO postResponseDTO) {
        Long postId = postResponseDTO.getPostId();
        List<LanguageDTO> languages = postLanguageService.saveLanguages(postRequestDTO.getLanguages(), postId);
        postResponseDTO.setLanguages(languages);
        List<LocationDTO> locations = postLocationService.saveLocations(postRequestDTO.getLocations(), postId);
        postResponseDTO.setLocations(locations);
        List<OccupationDTO> occupations = postOccupationService.saveOccupations(postRequestDTO.getOccupations(), postId);
        postResponseDTO.setOccupations(occupations);
    }

    private void resetPostValues(Long postId) {
        postLanguageService.resetPostLanguage(postId);
        postLocationService.resetPostLocation(postId);
        postOccupationService.resetPostOccupation(postId);
    }

    @Transactional
    public PostResponseDTO updatePost(Long postId, PostRequestDTO postRequestDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        User user = userRepository.findById(postRequestDTO.getUserId())
                .orElseThrow(UserNotFoundException::new);
        if (user.getId() != post.getUser().getId()) {
            throw new IsNotAuthorOfPostException();
        }

        post.setTitle(postRequestDTO.getTitle());
        post.setContent(postRequestDTO.getContent());
        setSpecificInfo(postRequestDTO, post);

        PostResponseDTO postResponseDTO = new PostResponseDTO(post);

        Type type = postResponseDTO.getType();
        setGroupInfo(post, postResponseDTO, type);

        if (type == Type.PROJECT || type == Type.MOGAKKO) {
            resetPostValues(postId);
            savePostValues(postRequestDTO, postResponseDTO);
        }

        return postResponseDTO;
    }

    @Transactional
    private void setSpecificInfo(PostRequestDTO postRequestDTO, Post post) {
        Type type = postRequestDTO.getType();
        switch (type) {
            case PROJECT:
                Project project = (Project) post;
                project.setDeadline(postRequestDTO.getDeadline());
                return;
            case MOGAKKO:
                Mogakko mogakko = (Mogakko) post;
                mogakko.setDeadline(postRequestDTO.getDeadline());
                mogakko.setTerm(postRequestDTO.getTerm());
                return;
            case QUESTION:
                Question question = (Question) post;
                return;
            case STUDY:
                Study study = (Study) post;
                return;
        }
    }

    public PostResponseDTO findOne(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        PostResponseDTO postResponseDTO = new PostResponseDTO(post);

        Type type = postResponseDTO.getType();
        setGroupInfo(post, postResponseDTO, type);

        return postResponseDTO;
    }

    private void setGroupInfo(Post post, PostResponseDTO postResponseDTO, Type type) {
        if (type == Type.PROJECT || type == Type.MOGAKKO) {
            Group group = groupRepository.findById(post.getGroup().getId()).get();
            postResponseDTO.setGroupId(group.getId());
            postResponseDTO.setGroupStatus(group.getGroupStatus());
        }
    }

    public List<PostResponseDTO> findPostsByType(String type) {
        if (!Arrays.stream(Type.values()).anyMatch(t -> t.name().equals(type))) {
            throw new NotValidPostTypeException();
        }
        List<Post> posts = postRepository.findByDtype(type);
        return posts.stream()
                .map(post -> {
                    PostResponseDTO postResponseDTO = new PostResponseDTO(post);
                    Type enumType = Type.valueOf(type);
                    setGroupInfo(post, postResponseDTO, enumType);
                    return postResponseDTO;
                })
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> findPostsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        List<Post> posts = user.getPosts();
        return posts.stream()
                .map(post -> {
                    PostResponseDTO postResponseDTO = new PostResponseDTO(post);
                    Type type = postResponseDTO.getType();
                    setGroupInfo(post, postResponseDTO, type);
                    return postResponseDTO;
                })
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> findStudyPostsByGroupId(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        return group.getStudyPosts().stream()
                .map(studyPost -> {
                    PostResponseDTO postResponseDTO = new PostResponseDTO((Post) studyPost);
                    return postResponseDTO;
                })
                .collect(Collectors.toList());
    }
}
