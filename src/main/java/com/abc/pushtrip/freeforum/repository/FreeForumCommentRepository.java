package com.abc.pushtrip.freeforum.repository;

import com.abc.pushtrip.freeforum.entity.FreeForumComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FreeForumCommentRepository extends JpaRepository<FreeForumComment, Long> {

    // 게시물 freeId로 댓글 전체 조회
    @Query("SELECT c FROM FreeForumComment c WHERE c.freeId = :freeId AND c.deleteYn = 'N' ORDER BY c.freeCommId DESC")
    List<FreeForumComment> findActiveFreeForumCommentByFreeId(String freeId);

}
