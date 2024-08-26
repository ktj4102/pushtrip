package com.abc.pushtrip.notice.repository;

import com.abc.pushtrip.notice.entity.NoticeForumComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeForumCommentRepository extends JpaRepository<NoticeForumComment, Long> {

    // 게시물 travelId로 댓글 전체 조회
    @Query("SELECT c FROM NoticeForumComment c WHERE c.noticeId = :noticeId AND c.deleteYn = 'N' ORDER BY c.noticeCommId DESC")
    List<NoticeForumComment> findActiveNoticeForumCommentByNoticeId(String noticeId);
}
