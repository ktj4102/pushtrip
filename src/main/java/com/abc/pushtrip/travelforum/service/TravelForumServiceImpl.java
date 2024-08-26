package com.abc.pushtrip.travelforum.service;

import com.abc.pushtrip.travelforum.dto.TravelForumSummary;
import com.abc.pushtrip.travelforum.entity.TravelForum;
import com.abc.pushtrip.travelforum.repository.TravelForumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class TravelForumServiceImpl implements TravelForumService {

    @Autowired
    private TravelForumRepository travelForumRepository;

    // Paging Select All
    @Override
    public Page<TravelForum> selectAll(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("insertDate"));
        Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));

        return travelForumRepository.findAll(pageable);
    }

    // 추가된 코드 핫플레이스 - 호기
    @Override
    public List<TravelForumSummary> top5(TravelForumSummary travelForumSummary) {
        Pageable pageable = PageRequest.of(0, 5);
        return travelForumRepository.findTop5TravelForumSummaries(pageable);
    }

    // Select All by title, userId
    @Override
    public Page<TravelForum> search(String searchCategory, String searchTerm, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("insertDate"));
        Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));

        return travelForumRepository.search(searchCategory, searchTerm, pageable);
    }

    // Insert
    @Override
    public void travelForumInsert(TravelForum travelForum) {
        System.out.println("TravelForumService Insert >>> true");

        travelForumRepository.save(travelForum);
    }

    // Select
    @Override
    public TravelForum travelForumSelect(TravelForum travelForum) {
        System.out.println("TravelForumService Select >>> true");

        TravelForum findTravelForum = travelForumRepository.findById(travelForum.getTravelId()).get();
        findTravelForum.setViewCount(findTravelForum.getViewCount()+1);
        travelForumRepository.save(findTravelForum);

        return findTravelForum;
    }

    // Update
    @Override
    public void travelForumUpdate(Long travelId, TravelForum travelForum, MultipartFile image) throws IOException {
        System.out.println("TravelForumService Update >>> true");

        TravelForum findTravelForum = travelForumRepository.findById(travelId)
                .orElseThrow(() -> new EntityNotFoundException("TravelForum not found"));
        findTravelForum.setTitle(travelForum.getTitle());
        findTravelForum.setContent(travelForum.getContent());
        if (!image.isEmpty()) {
            findTravelForum.setPictureFile(image.getBytes());
        }
        findTravelForum.setUpdateDate(new Date());
        travelForumRepository.save(findTravelForum);
    }

    // Delete
    @Override
    public void travelForumDelete(Long travelId) {
        System.out.println("TravelForumService Delete >>> true");

        // 엔티티가 데이터베이스에 존재하는지 확인
        Optional<TravelForum> existingTravelForum = travelForumRepository.findById(travelId);

        if (existingTravelForum.isPresent()) {
            // 엔티티가 존재하면 deleteyn 값을 "Y"로 설정하고 저장
            TravelForum forum = existingTravelForum.get();
            forum.setDeleteYn("Y");
            travelForumRepository.save(forum);
        } else {
            // 엔티티가 존재하지 않는 경우 처리
            System.out.println("ID not found : " + travelId);
        }
    }

    // getById
    @Override
    public TravelForum getTravelForumById(Long travelId) {
        return travelForumRepository.findById(travelId).orElse(null);
    }

    // SaveImage
    @Override
    public void saveImage(Long travelId, MultipartFile image) throws IOException {
        Optional<TravelForum> optionalTravelForum = travelForumRepository.findById(travelId);
        if (optionalTravelForum.isPresent()) {
            TravelForum travelForum = optionalTravelForum.get();
            travelForum.setPictureFile(image.getBytes());
            travelForumRepository.save(travelForum);
        } else {
            throw new RuntimeException("TravelForum not found");
        }
    }
}
