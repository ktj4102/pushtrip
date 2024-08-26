package com.abc.pushtrip.questionforum.service;

import com.abc.pushtrip.freeforum.entity.FreeForum;
import com.abc.pushtrip.questionforum.entity.QuestionForum;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface QuestionForumService {

    QuestionForum questionForumSelect(QuestionForum questionForum);
    void questionForumUpdate(Long questionId, QuestionForum questionForum, MultipartFile image) throws IOException;
    void questionForumDelete(Long questionId);
    QuestionForum getQuestionForumById(Long questionId);
    void saveImage(Long questionId, MultipartFile image) throws IOException;
    Page<QuestionForum> search(String searchCategory, String searchTerm, int page);
    // paging
    Page<QuestionForum> selectAll(int page);
    void questionForumInsert(QuestionForum questionForum);
}
