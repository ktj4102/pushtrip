package com.abc.pushtrip.travelforum.service;

import com.abc.pushtrip.travelforum.entity.TravelForumComment;
import com.abc.pushtrip.travelforum.repository.TravelForumCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TravelForumCommentServiceImpl implements TravelForumCommentService{

    @Autowired
    public TravelForumCommentRepository travelForumCommentRepository;

    // Select All
    @Override
    public List<TravelForumComment> travelForumCommentSelectAll(String travelId) {
        System.out.println("TravelForumCommentService Select All >>> : true");

        return travelForumCommentRepository.findActiveTravelForumCommentByTravelId(travelId);
    }

    // Insert
    @Override
    public void travelForumCommentInsert(TravelForumComment travelForumComment) {
        System.out.println("TravelForumCommentService Insert >>> : true");

        travelForumCommentRepository.save(travelForumComment);
    }

    // Delete
    @Override
    public void travelForumCommentDelete(Long travelCommId) {
        System.out.println("TravelForumCommentService Delete >>> true");

        // 엔티티가 데이터베이스에 존재하는지 확인
        Optional<TravelForumComment> existingTravelForumComment = travelForumCommentRepository.findById(travelCommId);

        if (existingTravelForumComment.isPresent()) {
            // 엔티티가 존재하면 deleteyn 값을 "Y"로 설정하고 저장
            TravelForumComment comment = existingTravelForumComment.get();
            comment.setDeleteYn("Y");
            travelForumCommentRepository.save(comment);
        } else {
            // 엔티티가 존재하지 않는 경우 처리
            System.out.println("ID not found : " + travelCommId);
        }
    }
}
