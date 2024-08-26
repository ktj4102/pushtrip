package com.abc.pushtrip.questionforum.service;

import com.abc.pushtrip.freeforum.entity.FreeForum;
import com.abc.pushtrip.questionforum.entity.QuestionForum;
import com.abc.pushtrip.questionforum.repository.QuestionForumRepository;
import com.abc.pushtrip.travelforum.entity.TravelForum;
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
public class QuestionForumServiceImpl implements QuestionForumService {

    @Autowired
    private QuestionForumRepository questionForumRepository;


    // Select All paging
    @Override
    public Page<QuestionForum> selectAll(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("insertDate"));
        Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));

        return questionForumRepository.findAll(pageable);
    }


    @Override
    public Page<QuestionForum> search(String searchCategory, String searchTerm, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("insertDate"));
        Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));

        return questionForumRepository.search(searchCategory, searchTerm, pageable);
    }

    // Insert
    @Override
    public void questionForumInsert(QuestionForum questionForum) {
        System.out.println("QuestionForumService Insert >>> true");

        questionForumRepository.save(questionForum);
    }

    // Select
    @Override
    public QuestionForum questionForumSelect(QuestionForum questionForum) {
        System.out.println("TravelForumService Select >>> true");

        QuestionForum findQuestionForum = questionForumRepository.findById(questionForum.getQuestionId()).get();
        findQuestionForum.setViewCount(findQuestionForum.getViewCount()+1);
        questionForumRepository.save(findQuestionForum);

        return findQuestionForum;
    }

    // Update
    @Override
    public void questionForumUpdate(Long questionId, QuestionForum questionForum, MultipartFile image) throws IOException {
        System.out.println("TravelForumService Update >>> true");

        QuestionForum findQuestionForum = questionForumRepository.findById( questionId)
                .orElseThrow(() -> new EntityNotFoundException("TravelForum not found"));
        findQuestionForum.setTitle(questionForum.getTitle());
        findQuestionForum.setContent(questionForum.getContent());
        if (!image.isEmpty()) {
            findQuestionForum.setPictureFile(image.getBytes());
        }
        findQuestionForum.setUpdateDate(new Date());
        questionForumRepository.save(findQuestionForum);
    }

    // Delete
    @Override
    public void  questionForumDelete(Long  questionId) {
        System.out.println("QuestionForumService Delete >>> true");

        // 엔티티가 데이터베이스에 존재하는지 확인
        Optional<QuestionForum> existingQuestionForum =  questionForumRepository.findById(questionId);

        if (existingQuestionForum.isPresent()) {
            // 엔티티가 존재하면 deleteyn 값을 "Y"로 설정하고 저장
            QuestionForum forum = existingQuestionForum.get();
            forum.setDeleteYn("Y");
            questionForumRepository.save(forum);
        } else {
            // 엔티티가 존재하지 않는 경우 처리
            System.out.println("ID not found : " + questionId);
        }
    }

    @Override
    public QuestionForum getQuestionForumById(Long questionId) {
        return questionForumRepository.findById(questionId).orElse(null);
    }


    @Override
    public void saveImage(Long questionId, MultipartFile image) throws IOException {
        Optional<QuestionForum> optionalQuestionForum = questionForumRepository.findById(questionId);
        if (optionalQuestionForum.isPresent()) {
            QuestionForum questionForum = optionalQuestionForum.get();
            questionForum.setPictureFile(image.getBytes());
            questionForumRepository.save(questionForum);
        } else {
            throw new RuntimeException("QuestionForum not found");
        }
    }
}
