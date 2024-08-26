package com.abc.pushtrip.tradeforum.service;

import com.abc.pushtrip.tradeforum.entity.TradeForumComment;
import com.abc.pushtrip.tradeforum.repository.TradeForumCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeForumCommentServiceImpl implements TradeForumCommentService{

    @Autowired
    public TradeForumCommentRepository tradeForumCommentRepository;

    // Select All
    @Override
    public List<TradeForumComment> tradeForumCommentSelectAll(String tradeId) {
        System.out.println("Comment Select All Service >>> : true");

        return tradeForumCommentRepository.findActiveTradeForumCommentByTradeId(tradeId);
    }

    // Insert
    @Override
    public void tradeForumCommentInsert(TradeForumComment tradeForumComment) {
        System.out.println("Comment Insert Service >>> : true");

        tradeForumCommentRepository.save(tradeForumComment);
    }

    // Delete
    @Override
    public void tradeForumCommentDelete(Long tradeCommId) {
        System.out.println("TradeForumCommentService Delete >>> true");

        // 엔티티가 데이터베이스에 존재하는지 확인
        Optional<TradeForumComment> existingTradeForumComment = tradeForumCommentRepository.findById(tradeCommId);

        if (existingTradeForumComment.isPresent()) {
            // 엔티티가 존재하면 deleteyn 값을 "Y"로 설정하고 저장
            TradeForumComment comment = existingTradeForumComment.get();
            comment.setDeleteYn("Y");
            tradeForumCommentRepository.save(comment);
        } else {
            // 엔티티가 존재하지 않는 경우 처리
            System.out.println("ID not found : " + tradeCommId);
        }
    }
//
//    // Delete
//    @Override
//    public void tradeForumCommentDelete(Long tradeCommId) {
//        System.out.println("Comment Delete Service >>> : true");
//
//        // 엔티티 존재하는지 확인
//        Optional<TradeForumComment> existingTradeForumComment = tradeForumCommentRepository.findById(tradeCommId);
//
//        if(existingTradeForumComment.isPresent()) {
//            // 엔티티가 존재하면 deleteyn 값을 "Y"로 설정하고 저장
//            TradeForumComment comment = existingTradeForumComment.get();
//            comment.setDeleteYn("Y");
//            tradeForumCommentRepository.save(comment);
//        } else {
//            // 엔티티가 존재하지 않는 경우 처리
//            System.out.println("해당 댓글이 존재하지 않습니다: " + tradeCommId);
//        }
//    }
}
