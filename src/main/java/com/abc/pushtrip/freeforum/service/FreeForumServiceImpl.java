package com.abc.pushtrip.freeforum.service;

import com.abc.pushtrip.freeforum.entity.FreeForum;
import com.abc.pushtrip.freeforum.repository.FreeForumRepository;
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
public class FreeForumServiceImpl implements FreeForumService{

    @Autowired
    private FreeForumRepository freeForumRepository;

    // Paging Select All
    @Override
    public Page<FreeForum> selectAll(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("insertDate"));
        Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));

        return freeForumRepository.findAll(pageable);
    }

    // Select All by title, userId
    @Override
    public Page<FreeForum> search(String searchCategory, String searchTerm, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("insertDate"));
        Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));

        return freeForumRepository.search(searchCategory, searchTerm, pageable);
    }

    // Insert
    @Override
    public void freeForumInsert(FreeForum freeForum) {
        System.out.println("FreeForumService Insert >>> true");

        freeForumRepository.save(freeForum);
    }

    // Select
    @Override
    public FreeForum freeForumSelect(FreeForum freeForum) {
        System.out.println("FreeForumService Select >>> true");

        FreeForum findFreeForum = freeForumRepository.findById(freeForum.getFreeId()).get();
        findFreeForum.setViewCount(findFreeForum.getViewCount()+1);
        freeForumRepository.save(findFreeForum);

        return findFreeForum;
    }

    // getById
    @Override
    public FreeForum getFreeForumById(Long freeId) {
        return freeForumRepository.findById(freeId).orElse(null);
    }

    // Update
    @Override
    public void freeForumUpdate(Long freeId, FreeForum freeForum, MultipartFile image) throws IOException {
        System.out.println("FreeForumService Update >>> true");

        FreeForum findFreeForum = freeForumRepository.findById(freeId)
                .orElseThrow(() -> new EntityNotFoundException("FreeForum not found"));
        findFreeForum.setTitle(freeForum.getTitle());
        findFreeForum.setContent(freeForum.getContent());
        if(!image.isEmpty()) {
            findFreeForum.setPictureFile(image.getBytes());
        }
        findFreeForum.setUpdateDate(new Date());
        freeForumRepository.save(findFreeForum);
    }

    // Delete
    @Override
    public void freeForumDelete(Long freeId) {
        System.out.println("FreeForumService Delete >>> true");

        // 엔티티가 데이터베이스에 존재하는지 확인
        Optional<FreeForum> existingFreeForum = freeForumRepository.findById(freeId);

        if (existingFreeForum.isPresent()) {
            // 엔티티가 존재하면 deleteyn 값을 "Y"로 설정하고 저장
            FreeForum forum = existingFreeForum.get();
            forum.setDeleteYn("Y");
            freeForumRepository.save(forum);
        } else {
            // 엔티티가 존재하지 않는 경우 처리
            System.out.println("ID not found : " + freeId);
        }
    }


    // SaveImage
    @Override
    public void saveImage(Long freeId, MultipartFile image) throws IOException {
        Optional<FreeForum> optionalFreeForum = freeForumRepository.findById(freeId);
        if (optionalFreeForum.isPresent()) {
            FreeForum freeForum = optionalFreeForum.get();
            freeForum.setPictureFile(image.getBytes());
            freeForumRepository.save(freeForum);
        } else {
            throw new RuntimeException("FreeForum not found");
        }
    }
}
