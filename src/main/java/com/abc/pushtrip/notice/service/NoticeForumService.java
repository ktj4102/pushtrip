package com.abc.pushtrip.notice.service;

import com.abc.pushtrip.notice.entity.NoticeForum;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface NoticeForumService {

    NoticeForum noticeForumSelect(NoticeForum noticeForum);
    void noticeForumUpdate(Long noticeId, NoticeForum noticeForum, MultipartFile image) throws IOException;
    void noticeForumDelete(Long noticeId);
    NoticeForum getNoticeForumById(Long noticeId);
    void saveImage(Long noticeId, MultipartFile image) throws IOException;
    Page<NoticeForum> search(String searchCategory, String searchTerm, int page);
    // paging
    Page<NoticeForum> selectAll(int page);
    void noticeForumInsert(NoticeForum noticeForum);
}
