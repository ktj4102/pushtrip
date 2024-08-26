package com.abc.pushtrip.notice.service;

import com.abc.pushtrip.notice.entity.NoticeForumComment;


import java.util.List;

public interface NoticeForumCommentService {


    List<NoticeForumComment> noticeForumCommentSelectAll(String noticeId);
    void noticeForumCommentInsert(NoticeForumComment noticeForumComment);
    void noticeForumCommentDelete(Long noticeCommId);
    // void travelForumCommentDelete(Long travelCommId);
    // void travelForumCommentUpdate(Long travelCommId, TravelForumComment travelForumComment);

}
