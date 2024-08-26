package com.abc.pushtrip.freeforum.service;

import com.abc.pushtrip.freeforum.entity.FreeForumComment;
import com.abc.pushtrip.freeforum.repository.FreeForumCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FreeForumCommentServiceImpl implements FreeForumCommentService {

    @Autowired
    public FreeForumCommentRepository freeForumCommentRepository;

    // Select All
    @Override
    public List<FreeForumComment> freeForumCommentSelectAll(String freeId) {
        System.out.println("FreeForumCommentService Select All >>> : true");

        return freeForumCommentRepository.findActiveFreeForumCommentByFreeId(freeId);
    }

    // Insert
    @Override
    public void freeForumCommentInsert(FreeForumComment freeForumComment) {
        System.out.println("FreeForumCommentService Insert >>> : true");

        freeForumCommentRepository.save(freeForumComment);
    }

    // Delete
    @Override
    public void freeForumCommentDelete(Long freeCommId) {
        System.out.println("FreeForumCommentService Delete >>> true");

        // 엔티티가 데이터베이스에 존재하는지 확인
        Optional<FreeForumComment> existingFreeForumComment = freeForumCommentRepository.findById(freeCommId);

        if (existingFreeForumComment.isPresent()) {
            // 엔티티가 존재하면 deleteyn 값을 "Y"로 설정하고 저장
            FreeForumComment comment = existingFreeForumComment.get();
            comment.setDeleteYn("Y");
            freeForumCommentRepository.save(comment);
        } else {
            // 엔티티가 존재하지 않는 경우 처리
            System.out.println("ID not found : " + freeCommId);
        }
    }
}
