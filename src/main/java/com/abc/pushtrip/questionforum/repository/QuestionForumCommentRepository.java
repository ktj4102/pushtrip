package com.abc.pushtrip.questionforum.repository;

import com.abc.pushtrip.questionforum.entity.QuestionForumComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionForumCommentRepository extends JpaRepository<QuestionForumComment, Long> {

    // 게시물 travelId로 댓글 전체 조회
    @Query("SELECT c FROM QuestionForumComment c WHERE c.questionId = :questionId AND c.deleteYn = 'N' ORDER BY c.questionCommId DESC")
    List<QuestionForumComment> findActiveQuestionForumCommentByQuestionId(String questionId);
}
