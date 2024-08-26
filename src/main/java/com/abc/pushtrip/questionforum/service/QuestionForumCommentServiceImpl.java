package com.abc.pushtrip.questionforum.service;

import com.abc.pushtrip.questionforum.entity.QuestionForum;
import com.abc.pushtrip.questionforum.entity.QuestionForumComment;
import com.abc.pushtrip.questionforum.repository.QuestionForumCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionForumCommentServiceImpl implements QuestionForumCommentService {

    @Autowired
    public QuestionForumCommentRepository questionForumCommentRepository;

    // Select All
    @Override
    public List<QuestionForumComment> questionForumCommentSelectAll(String questionId) {
        System.out.println("Comment Select All Service >>> : true");

        return questionForumCommentRepository.findActiveQuestionForumCommentByQuestionId(questionId);
    }

    // Insert
    @Override
    public void questionForumCommentInsert(QuestionForumComment questionForumComment) {
        System.out.println("Comment Insert Service >>> : true");

        questionForumCommentRepository.save(questionForumComment);
    }



    // Delete
    @Override
    public void questionForumCommentDelete(Long questionCommId) {
        System.out.println("QuestionForumCommentService Delete >>> true");

        // 엔티티가 데이터베이스에 존재하는지 확인
        Optional<QuestionForumComment> existingQuestionForumComment = questionForumCommentRepository.findById(questionCommId);

        if (existingQuestionForumComment.isPresent()) {
            // 엔티티가 존재하면 deleteyn 값을 "Y"로 설정하고 저장
            QuestionForumComment comment = existingQuestionForumComment.get();
            comment.setDeleteYn("Y");
            questionForumCommentRepository.save(comment);
        } else {
            // 엔티티가 존재하지 않는 경우 처리
            System.out.println("ID not found : " + questionCommId);
        }
    }
//
//    // Delete
//    @Override
//    public void travelForumCommentDelete(Long travelCommId) {
//        System.out.println("Comment Delete Service >>> : true");
//
//        // 엔티티 존재하는지 확인
//        Optional<TravelForumComment> existingTravelForumComment = travelForumCommentRepository.findById(travelCommId);
//
//        if(existingTravelForumComment.isPresent()) {
//            // 엔티티가 존재하면 deleteyn 값을 "Y"로 설정하고 저장
//            TravelForumComment comment = existingTravelForumComment.get();
//            comment.setDeleteYn("Y");
//            travelForumCommentRepository.save(comment);
//        } else {
//            // 엔티티가 존재하지 않는 경우 처리
//            System.out.println("해당 댓글이 존재하지 않습니다: " + travelCommId);
//        }
//    }
}


