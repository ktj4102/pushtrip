package com.abc.pushtrip.questionforum.controller;


import com.abc.pushtrip.freeforum.entity.FreeForum;
import com.abc.pushtrip.questionforum.entity.QuestionForum;
import com.abc.pushtrip.questionforum.service.QuestionForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@SpringBootApplication
public class QuestionForumController {

    @Autowired
    private QuestionForumService questionForumService;

    // Select All 을 거쳐서 와야 게시글이 뜸
    @GetMapping("/board_question")
    public String board_question() {
        return "thymeleaf/board_question";
    }

    // Insert Form
    @GetMapping("board_question_content_write")
    public String insertForm(Model model) {
        model.addAttribute("questionForumInsert", new QuestionForum());
        return "thymeleaf/board_question_content_write";
    }

    // Select Detail
    @GetMapping("/board_question_content")
    public String selectDetail(Model model){
        return "thymeleaf/board_question_content";
    }

    // Update Form
    @GetMapping("/board_question_update")
    public String updateForm(@RequestParam("questionId") Long questionId, Model model){
        QuestionForum questionForum = questionForumService.getQuestionForumById(questionId);
        model.addAttribute("questionForum", questionForum);
        return "thymeleaf/board_question_update";
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Select All
    @GetMapping("/questionForumSelectAll")
    public String questionForumSelectAll(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<QuestionForum> paging = questionForumService.selectAll(page);
        model.addAttribute("paging", paging);
        return "thymeleaf/board_question";
    }

    // Search
    @GetMapping("/questionForumSearch")
    public String searchPosts(Model model,
                              @RequestParam(value="searchCategory", defaultValue="title") String searchCategory,
                              @RequestParam(value="searchTerm", defaultValue="") String searchTerm,
                              @RequestParam(value="page", defaultValue="0") int page) {
        Page<QuestionForum> paging = questionForumService.search(searchCategory, searchTerm, page);
        model.addAttribute("paging", paging);
        return "thymeleaf/board_question";
    }

    // Insert
    @PostMapping("/questionForumInsert")
    public String questionForumInsert(@ModelAttribute("questionForumInsert") QuestionForum questionForum, BindingResult result, @RequestParam("image") MultipartFile image) {
        if (result.hasErrors()) {
            return "thymeleaf/insertForm";
        }
        questionForumService.questionForumInsert(questionForum);
        try {
            questionForumService.saveImage(questionForum.getQuestionId(), image);
        } catch (IOException e) {
            // 이미지 저장 실패 처리
            e.printStackTrace();
        }
        return "redirect:/questionForumSelectAll";
    }

    // Select
    @GetMapping("/questionForumSelect")
    public String questionForumSelect(@RequestParam("questionId") Long questionId, Model model) {
        QuestionForum questionForum = new QuestionForum();
        questionForum.setQuestionId(questionId);
        QuestionForum selectedQuestionForum = questionForumService.questionForumSelect(questionForum);
        model.addAttribute("questionForum", selectedQuestionForum);
        return "thymeleaf/board_question_content";
    }

    // Update
    @PostMapping("/questionForumUpdate")
    public String questionForumUpdate(@RequestParam("questionId") Long questionId,
                                  @ModelAttribute QuestionForum questionForum,
                                  @RequestParam("image") MultipartFile image) throws IOException {
        questionForumService.questionForumUpdate(questionId, questionForum, image);
        return "redirect:/questionForumSelectAll";
    }

    // Delete
    @PostMapping("/questionForumDelete")
    public String questionForumDelete(@RequestParam("questionId") Long questionId) {
        QuestionForum questionForum = new QuestionForum();
        questionForum.setQuestionId(questionId);
        questionForumService.questionForumDelete(questionId);
        return "redirect:/questionForumSelectAll";
    }
}
