package com.abc.pushtrip.tradeforum.repository;

import com.abc.pushtrip.tradeforum.entity.TradeForumComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TradeForumCommentRepository extends JpaRepository<TradeForumComment, Long> {

    // 게시물 tradeId로 댓글 전체 조회
    @Query("SELECT c FROM TradeForumComment c WHERE c.tradeId = :tradeId AND c.deleteYn = 'N' ORDER BY c.tradeCommId DESC")
    List<TradeForumComment> findActiveTradeForumCommentByTradeId(String tradeId);

}
