# 모각코/프로젝트를 구하고 관리할 수 있는 웹서비스(server)

### 일정
- 2022-11-01 ~ 2022-11-07 : 프로젝트 주제 선정
- 2022-11-07 ~ 2022-11-13 : 기획
- 2022-11-13 ~ 2022-11-20 : 세부요구사항 회의 및 작성
- 2022-11-20 ~ 2022-12-01 : er diagram 작성
- 2022-12-01 ~ 2022-12-23 : api 명세서 작성
- 2022-12-31 ~ 2022-01-26 : 서버 개발
- 2022-01-26 ~ 2022-02-14 : 프론트 개발

### 사용 기술
- 백엔드 : Java Spring, jpa
- 프론트엔드 : React
- database : MySQL, aws rds
- 서버 배포 : aws ec2

### 요구사항 분석
https://www.notion.so/11-13-6204653820c54bde9a2a7c2430584fb5

### db 설계
![mogakko erd](https://user-images.githubusercontent.com/81623224/218642610-0156a06a-409c-4bbb-bfc5-df8c811bb643.png)
- many-to-many 관계는 many-to-one, one-to-many 관계로 풀어내었습니다.
- 게시글(post)은 여러 종류가 있기에 상속 관계 매핑을 사용하였습니다. 조인을 하지 않는 단일 테이블 전략을 사용하여 조회 속도를 빠르게 하였습니다.

### API 설계
https://www.notion.so/API-70fe64361c8149b983b2d36e35329f9c
- RESTful한 api를 작성하려 노력하였습니다.
- 다음 사이트를 통해 rest api를 작성하는데 어떤 규칙을 지켜야 하는지 파악했습니다.
  - https://restfulapi.net/resource-naming/
- gmail의 API들을 보며 api를 hierarchial하게 작성한다는 것이 무엇인지 이해할 수 있었습니다.
  - https://developers.google.com/gmail/api/reference/rest
  - http://blog.storyg.co/rest-api-response-body-best-pratics

### 화면
- 유저의 로그인 여부에 따라 접근할 수 있는 페이지를 제한하였습니다.
#### 회원가입
![RegisterPage](https://user-images.githubusercontent.com/81623224/218650637-7f451f6e-2d30-468d-abd2-d7de1562ebca.png)

#### 로그인
![LoginPage](https://user-images.githubusercontent.com/81623224/218650620-c65fc72a-5f84-4a8c-afce-564db8ee790d.png)

#### 유저 프로필
![ProfilePage](https://user-images.githubusercontent.com/81623224/218650636-efad0122-847c-48f5-8a86-5774233a024c.png)

#### 프로필 수정
![EditProfilePage](https://user-images.githubusercontent.com/81623224/218650606-fb26107e-2961-4d25-80b1-79148ade477c.png)

#### 게시글 리스트
![PostListPage](https://user-images.githubusercontent.com/81623224/218650634-9560a053-2198-4e25-81de-b62f60778cb8.png)
```
프로젝트와 모각코 게시글 페이지가 따로 있습니다.
사용 언어, 선호 지역, 선호 직무에 따라 필터링을 할 수 있습니다.
또, 모집중인 게시글만 확인할 수 있습니다.
```

#### 게시글 상세
![PostDetailPage](https://user-images.githubusercontent.com/81623224/218650631-99e19ebe-9d35-4b5a-b200-1911337f94f3.png)
```
게시글에 댓글을 작성할 수 있습니다.
게시글에 해당하는 그룹에 지원할 수 있습니다.
```

#### 새 게시글 작성
![NewPostPage](https://user-images.githubusercontent.com/81623224/218650626-c19937cc-fd47-44d5-bb16-6771394dc1eb.png)

#### 내가 속한 그룹 리스트
![MyGroupListPage](https://user-images.githubusercontent.com/81623224/218650624-b15d4206-4d8a-47c5-959a-21a5f24a2d6b.png)
```
게시글을 작성하면 그에 해당하는 그룹이 자동으로 생성됩니다.
이 페이지에서 본인이 속한 그룹 리스트를 확인할 수 있습니다.
```

#### 그룹 상세 페이지
![GroupDetailPage](https://user-images.githubusercontent.com/81623224/218650615-b27e6ca3-db0e-421a-b76e-12d1e8a624a9.png)
```
윗쪽의 게시글 확인, 모집 마감, 그룹 종료, 그룹 신청자 기능은 그룹장에게만 보이는 버튼입니다.
이 페이지에서 그룹 내 모임을 만들 수 있고, 공부 내용과 같은 게시글을 작성할 수 있습니다.
```

#### 그룹 참가 신청자
![GroupApplicantsList](https://user-images.githubusercontent.com/81623224/218650612-ec07e346-b06a-4948-ac2b-5d44d777268b.PNG)
```
그룹에 지원한 유저들을 확인할 수 있습니다.
```

#### 평가
![EvaluationPage](https://user-images.githubusercontent.com/81623224/218650610-b87a6045-0236-441b-89dc-dd3bf9b6a223.PNG)
```
그룹이 완전히 종료되면 서로에게 평가를 남길 수 있습니다.
작성한 평가는 해당 유저의 프로필에 표시됩니다.
```

### 버그
1. 등록, 수정, 삭제하는 여러 service들이 각자의 Transactional로 처리될 때의 문제
```java
public class PostController {
  @PostMapping("/posts")
  public PostResponseDTO addPost(@RequestBody PostRequestDTO postRequestDTO) {
    PostResponseDTO postResponseDTO = postService.savePost(postRequestDTO);
    Long postId = postResponseDTO.getPostId();

    List<LanguageDTO> languages = postLanguageService.saveLanguages(postRequestDTO.getLanguages(), postId);
    postResponseDTO.setLanguages(languages);
    List<LocationDTO> locations = postLocationService.saveLocations(postRequestDTO.getLocations(), postId);
    postResponseDTO.setLocations(locations);
    List<OccupationDTO> occupations = postOccupationService.saveOccupations(postRequestDTO.getOccupations(), postId);
    postResponseDTO.setOccupations(occupations);

    return postResponseDTO;
  }
}
```
- 문제 상황
  - 하나의 controller에서 data를 등록, 수정, 삭제하는 service를 여러개 호출하고 있다.
  - savePost -> saveLanguages -> saveLocations -> saveOccupations
  - 만약 savePost까지 성공하고 saveLanguages에서 exception이 발생한다면? post만 저장이 되고, 뒤의 languages, locations, occupations는 저장이 되지 않는다.
- 해결
```java
public class PostController {
  @PostMapping("/posts")
  public PostResponseDTO addPost(@RequestBody PostRequestDTO postRequestDTO) {
    return postService.savePost(postRequestDTO);
  }
}
```
  - 우리가 원하는 것은 data를 저장하는 4개의 service 중 하나에서 exception이 발생한다면, 모든 작업을 rollback하는 것이다.
  - 이 4개의 service를 하나의 Transactional로 처리하기 위해 이 4가지 작업을 하나의 service에서 처리하고, controller에서는 단순히 그 service를 호출하도록 하였다.

2. foreign key constraint fails
   - comment 삭제에서, 해당 comment를 root로 갖는 모든 comment들을 삭제하도록 구현했는데 foreign key constraint fails가 뜸.
   - 해당 comment를 root로 갖는 comment들을 먼저 삭제한 후 root comment를 삭제하도록 하여 해결함.

3. jpa
```java
class GroupServiceTest {
  @Test
  @DisplayName("Group master가 group member 한 명을 퇴출시킨다.")
  void deleteGroupMember() {
    UserIdDTO userIdDTO = new UserIdDTO();
    userIdDTO.setUserId(user1.getId());
    groupService.deleteGroupMember(group.getId(), user2.getId(), userIdDTO);

    Group findGroup = groupRepository.findById(group.getId()).get();
    assertThat(findGroup.getGroupUsers()).as("member 한 명이 퇴출되어, 그룹에는 1명만 남아있다.")
            .hasSize(1);
  }
}
```
- 문제 상황
  - 위의 결과는 delete가 수행되지 않은 것처럼 2가 나온다.(expect : 1)
  - groupService.deleteGroupMember의 코드를 보면 알 수 있는데, db 상의 groupUser는 삭제되었지만, group 객체의 groupUsers에서는 삭제가 되지 않았기 때문이었다.
  - 따라서, findGroup.getGroupUsers()는 여전히 2개의 groupUser를 갖고 있는 것이다.
- 해결
```java
class GroupServiceTest {
  @Test
  @DisplayName("Group master가 group member 한 명을 퇴출시킨다.")
  void deleteGroupMember() {
    UserIdDTO userIdDTO = new UserIdDTO();
    userIdDTO.setUserId(user1.getId());
    groupService.deleteGroupMember(group.getId(), user2.getId(), userIdDTO);

    List<GroupUser> groupUsers = groupUserRepository.findByGroup(group);
    assertThat(groupUsers).as("member 한 명이 퇴출되어, 그룹에는 1명만 남아있다.")
            .hasSize(1);
  }
}
```
- db에서 groupUsers를 조회하여 assert하였다.

### 예외 처리
- 발생할 수 있는 여러 예외 상황에 대해 상세하게 처리하기 위해 노력했습니다.
```java
public enum BadRequestCode {
    NOT_FOUND_ERROR_CODE("COMMON-001", "해당 에러의 에러코드를 찾을 수 없습니다.", NotFoundErrorCodeException.class),

    COMMENT_NOT_FOUND("COMMENT-001", "존재하지 않는 댓글입니다.", CommentNotFoundException.class),
    ROOT_COMMENT_NOT_FOUND("COMMENT-002", "존재하지 않는 부모댓글입니다.", RootCommentNotFoundException.class),
    ROOT_COMMENT_NOT_BELONG_TO_POST("COMMENT-003", "해당 게시글에 속하는 부모댓글이 아닙니다.",
            RootCommentNotBelongToPostException.class),
    ROOT_COMMENT_HAS_ANOTHER_ROOT_COMMENT("COMMENT-004", "부모댓글이 다른 부모댓글을 가집니다.",
            RootCommentHasAnotherRootCommentException.class),

    EVALUATION_NOT_FOUND("EVALUATION-001", "존재하지 않는 평가입니다.", EvaluationNotFoundException.class),
    EVALUATED_USER_NOT_FOUND("EVALUATION-002", "평가받는 유저가 존재하지 않습니다.", EvaluatedUserNotFoundException.class),
    EVALUATING_USER_NOT_FOUND("EVALUATION-003", "평가하는 유저가 존재하지 않습니다.", EvaluatingUserNotFoundException.class),
    EVALUATED_USER_NOT_BELONG_TO_GROUP("EVALUATION-004", "평가받는 유저가 해당 그룹에 속하지 않습니다.", EvaluatedUserNotBelongToGroupException.class),
    EVALUATING_USER_NOT_BELONG_TO_GROUP("EVALUATION-005", "평가하는 유저가 해당 그룹에 속하지 않습니다.", EvaluatingUserNotBelongToGroupException.class),
    ALREADY_EVALUATED("EVALUATION-006", "이미 평가하였습니다.", AlreadyEvaluatedException.class),

    GROUP_NOT_FOUND("GROUP-001", "존재하지 않는 그룹입니다.", GroupNotFoundException.class),
    ALREADY_APPLIED("GROUP-002", "이미 가입신청한 그룹입니다.", AlreadyAppliedException.class),
    IS_NOT_GROUP_MASTER("GROUP-003", "그룹장이 아닙니다.", IsNotGroupMasterException.class),
    NOT_RECRUITING_GROUP("GROUP-004", "모집중인 그룹이 아닙니다.", NotRecruitingGroupException.class),

    MEETING_NOT_FOUND("MEETING-001", "존재하지 않는 모임입니다.", MeetingNotFoundException.class),

    POST_NOT_FOUND("POST-001", "존재하지 않는 게시글입니다.", PostNotFoundException.class),
    IS_NOT_AUTHOR_OF_POST("POST-002", "게시글의 작성자가 아닙니다.", IsNotAuthorOfPostException.class),
    NOT_VALID_POST_TYPE("POST-003", "게시글 타입이 올바르지 않습니다.", NotValidPostTypeException.class),

    USER_NOT_FOUND("USER-001", "존재하지 않는 유저입니다.", UserNotFoundException.class),
    LOGIN_FAILED("USER-002", "로그인에 실패하였습니다.", LoginFailedException.class),

    LANGUAGE_NOT_FOUND("LANGUAGE-001", "존재하지 않는 언어입니다.", LanguageNotFoundException.class),
    LOCATION_NOT_FOUND("LOCATION-001", "존재하지 않는 지역입니다.", LocationNotFoundException.class),
    OCCUPATION_NOT_FOUND("OCCUPATION-001", "존재하지 않는 직무입니다.", OccupationNotFoundException.class),
    ;
    private String code;
    private String message;
    private Class<? extends BadRequestException> type;
}
```


### 성능을 신경 쓴 부분
- jpa
  - service class에 Transactional을 읽기 전용으로 선언하고, 엔티티의 등록, 수정, 삭제가 필요한 메서드에만 @Transactional을 선언하여 읽기 작업만 하는 메서드에 대해서는 스냅샷 저장, 변경감지 등의 작업을 수행하지 않도록 하였다.
  ```java
  @Transactional(readOnly = true)
  public class GroupService {
    public GroupStatusResponseDTO getGroupStatus(Long groupId) {
      Group group = groupRepository.findById(groupId)
              .orElseThrow(GroupNotFoundException::new);
  
      return new GroupStatusResponseDTO(group);
    }
  
    @Transactional
    public GroupStatusResponseDTO setGroupStatus(Long groupId, GroupStatus groupStatus) {
      Group group = groupRepository.findById(groupId)
              .orElseThrow(GroupNotFoundException::new);
  
      group.setGroupStatus(groupStatus);
  
      return new GroupStatusResponseDTO(group);
    }
  }
  ```
  - FetchType.LAZY

### 테스트
- controller와 service에 대해 단위 테스트를 작성하였습니다.