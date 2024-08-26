package com.abc.pushtrip.questionforum.service;

import com.abc.pushtrip.questionforum.entity.QuestionForumComment;

import java.util.List;

public interface QuestionForumCommentService {


    List<QuestionForumComment> questionForumCommentSelectAll(String questionId);
    void questionForumCommentInsert(QuestionForumComment questionForumComment);
    void questionForumCommentDelete(Long questionCommId);
    // void travelForumCommentDelete(Long travelCommId);
    // void travelForumCommentUpdate(Long travelCommId, TravelForumComment travelForumComment);

}
