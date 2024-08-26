package com.abc.pushtrip.freeforum.service;

import com.abc.pushtrip.freeforum.entity.FreeForum;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FreeForumService {

    // Paging Select All
    Page<FreeForum> selectAll(int page);

    // Select All by title, userId
    Page<FreeForum> search(String searchCategory, String searchTerm, int page);

    // Insert
    void freeForumInsert(FreeForum freeForum);

    // Select
    FreeForum freeForumSelect(FreeForum freeForum);
    FreeForum getFreeForumById(Long freeId);

    // Update
    void freeForumUpdate(Long freeId, FreeForum freeForum, MultipartFile image) throws IOException;

    // Delete
    void freeForumDelete(Long freeId);

    // saveImage
    void saveImage(Long freeId, MultipartFile image) throws IOException;
}
