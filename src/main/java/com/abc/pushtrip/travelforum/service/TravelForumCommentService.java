package com.abc.pushtrip.travelforum.service;

import com.abc.pushtrip.travelforum.entity.TravelForumComment;

import java.util.List;

public interface TravelForumCommentService {

    List<TravelForumComment> travelForumCommentSelectAll(String travelId);
    void travelForumCommentInsert(TravelForumComment travelForumComment);
    void travelForumCommentDelete(Long travelCommId);
    // void travelForumCommentUpdate(Long travelCommId, TravelForumComment travelForumComment);

}
