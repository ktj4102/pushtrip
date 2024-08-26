package com.abc.pushtrip.notice.service;

import com.abc.pushtrip.notice.entity.NoticeForum;
import com.abc.pushtrip.notice.repository.NoticeForumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeForumServiceImpl implements NoticeForumService {

    @Autowired
    private NoticeForumRepository noticeForumRepository;


    // Select All paging
    @Override
    public Page<NoticeForum> selectAll(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("insertDate"));
        Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));

        return noticeForumRepository.findAll(pageable);
    }


    @Override
    public Page<NoticeForum> search(String searchCategory, String searchTerm, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("insertDate"));
        Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));

        return noticeForumRepository.search(searchCategory, searchTerm, pageable);
    }

    // Insert
    @Override
    public void noticeForumInsert(NoticeForum noticeForum) {
        System.out.println("QuestionForumService Insert >>> true");

        noticeForumRepository.save(noticeForum);
    }

    // Select
    @Override
    public NoticeForum noticeForumSelect(NoticeForum noticeForum) {
        System.out.println("TravelForumService Select >>> true");

        NoticeForum findNoticeForum = noticeForumRepository.findById(noticeForum.getNoticeId()).get();
        findNoticeForum.setViewCount(findNoticeForum.getViewCount()+1);
        noticeForumRepository.save(findNoticeForum);

        return findNoticeForum;
    }

    // Update
    @Override
    public void noticeForumUpdate(Long noticeId, NoticeForum noticeForum, MultipartFile image) throws IOException {
        System.out.println("TravelForumService Update >>> true");

        NoticeForum findNoticeForum = noticeForumRepository.findById(noticeId)
                .orElseThrow(() -> new EntityNotFoundException("NoticeForum not found"));
        findNoticeForum.setTitle(noticeForum.getTitle());
        findNoticeForum.setContent(noticeForum.getContent());
        if (!image.isEmpty()) {
            findNoticeForum.setPictureFile(image.getBytes());
        }
        findNoticeForum.setUpdateDate(new Date());
        noticeForumRepository.save(findNoticeForum);
    }

    // Delete
    @Override
    public void  noticeForumDelete(Long  noticeId) {
        System.out.println("QuestionForumService Delete >>> true");

        // 엔티티가 데이터베이스에 존재하는지 확인
        Optional<NoticeForum> existingNoticeForum =  noticeForumRepository.findById(noticeId);

        if (existingNoticeForum.isPresent()) {
            // 엔티티가 존재하면 deleteyn 값을 "Y"로 설정하고 저장
            NoticeForum forum = existingNoticeForum.get();
            forum.setDeleteYn("Y");
            noticeForumRepository.save(forum);
        } else {
            // 엔티티가 존재하지 않는 경우 처리
            System.out.println("ID not found : " + noticeId);
        }
    }

    @Override
    public NoticeForum getNoticeForumById(Long noticeId) {
        return noticeForumRepository.findById(noticeId).orElse(null);
    }


    @Override
    public void saveImage(Long noticeId, MultipartFile image) throws IOException {
        Optional<NoticeForum> optionalNoticeForum = noticeForumRepository.findById(noticeId);
        if (optionalNoticeForum.isPresent()) {
            NoticeForum noticeForum = optionalNoticeForum.get();
            noticeForum.setPictureFile(image.getBytes());
            noticeForumRepository.save(noticeForum);
        } else {
            throw new RuntimeException("NoticeForum not found");
        }
    }
}
