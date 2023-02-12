package com.example.mogakko.global.exception;

import com.example.mogakko.domain.comment.exception.CommentNotFoundException;
import com.example.mogakko.domain.comment.exception.RootCommentHasAnotherRootCommentException;
import com.example.mogakko.domain.comment.exception.RootCommentNotBelongToPostException;
import com.example.mogakko.domain.comment.exception.RootCommentNotFoundException;
import com.example.mogakko.domain.evaluation.exception.*;
import com.example.mogakko.domain.group.exception.AlreadyAppliedException;
import com.example.mogakko.domain.group.exception.GroupNotFoundException;
import com.example.mogakko.domain.group.exception.IsNotGroupMasterException;
import com.example.mogakko.domain.group.exception.NotRecruitingGroupException;
import com.example.mogakko.domain.meeting.exception.MeetingNotFoundException;
import com.example.mogakko.domain.post.exception.IsNotAuthorOfPostException;
import com.example.mogakko.domain.post.exception.NotValidPostTypeException;
import com.example.mogakko.domain.post.exception.PostNotFoundException;
import com.example.mogakko.domain.user.exception.LoginFailedException;
import com.example.mogakko.domain.user.exception.UserNotFoundException;
import com.example.mogakko.domain.values.exception.LanguageNotFoundException;
import com.example.mogakko.domain.values.exception.LocationNotFoundException;
import com.example.mogakko.domain.values.exception.OccupationNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
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

    public static BadRequestCode findByClass(Class<?> type) {
        return Arrays.stream(BadRequestCode.values())
            .filter(code -> code.type.equals(type))
            .findAny()
            .orElseThrow(NotFoundErrorCodeException::new);
    }

}
