package com.abc.pushtrip.tradeforum.service;

import com.abc.pushtrip.tradeforum.entity.TradeForumComment;

import java.util.List;

public interface TradeForumCommentService {

    List<TradeForumComment> tradeForumCommentSelectAll(String tradeId);
    void tradeForumCommentInsert(TradeForumComment tradeForumComment);
    void tradeForumCommentDelete(Long tradeCommId);
    // void tradeForumCommentDelete(Long tradeCommId);
    // void tradeForumCommentUpdate(Long tradeCommId, TradeForumComment tradeForumComment);

}
