package com.abc.pushtrip.notice.service;

import com.abc.pushtrip.notice.entity.NoticeForumComment;
import com.abc.pushtrip.notice.repository.NoticeForumCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeForumCommentServiceImpl implements NoticeForumCommentService {

    @Autowired
    public NoticeForumCommentRepository noticeForumCommentRepository;

    // Select All
    @Override
    public List<NoticeForumComment> noticeForumCommentSelectAll(String noticeId) {
        System.out.println("Comment Select All Service >>> : true");

        return noticeForumCommentRepository.findActiveNoticeForumCommentByNoticeId(noticeId);
    }

    // Insert
    @Override
    public void noticeForumCommentInsert(NoticeForumComment noticeForumComment) {
        System.out.println("Comment Insert Service >>> : true");

        noticeForumCommentRepository.save(noticeForumComment);
    }



    // Delete
    @Override
    public void noticeForumCommentDelete(Long noticeCommId) {
        System.out.println("NoticeForumCommentService Delete >>> true");

        // 엔티티가 데이터베이스에 존재하는지 확인
        Optional<NoticeForumComment> existingNoticeForumComment = noticeForumCommentRepository.findById(noticeCommId);

        if (existingNoticeForumComment.isPresent()) {
            // 엔티티가 존재하면 deleteyn 값을 "Y"로 설정하고 저장
            NoticeForumComment comment = existingNoticeForumComment.get();
            comment.setDeleteYn("Y");
            noticeForumCommentRepository.save(comment);
        } else {
            // 엔티티가 존재하지 않는 경우 처리
            System.out.println("ID not found : " + noticeCommId);
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


