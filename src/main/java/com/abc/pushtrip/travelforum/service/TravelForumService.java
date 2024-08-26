package com.abc.pushtrip.travelforum.service;

import com.abc.pushtrip.travelforum.dto.TravelForumSummary;
import com.abc.pushtrip.travelforum.entity.TravelForum;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TravelForumService {

    // Paging Select All
    Page<TravelForum> selectAll(int page);

    // 추가된 코드 핫플레이스 - 호기
    List<TravelForumSummary> top5(TravelForumSummary travelForumSummary);

    // Select All by title, userId
    Page<TravelForum> search(String searchCategory, String searchTerm, int page);

    // Insert
    void travelForumInsert(TravelForum travelForum);

    // Select
    TravelForum travelForumSelect(TravelForum travelForum);
    TravelForum getTravelForumById(Long travelId);

    // Update
    void travelForumUpdate(Long travelId, TravelForum travelForum, MultipartFile image) throws IOException;

    // Delete
    void travelForumDelete(Long travelId);

    // saveImage
    void saveImage(Long travelId, MultipartFile image) throws IOException;

}
