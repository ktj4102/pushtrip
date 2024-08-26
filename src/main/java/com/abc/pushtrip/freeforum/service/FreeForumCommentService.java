package com.abc.pushtrip.freeforum.service;

import com.abc.pushtrip.freeforum.entity.FreeForumComment;

import java.util.List;

public interface FreeForumCommentService {
    List<FreeForumComment> freeForumCommentSelectAll(String freeId);
    void freeForumCommentInsert(FreeForumComment freeForumComment);
    void freeForumCommentDelete(Long freeCommId);

}
