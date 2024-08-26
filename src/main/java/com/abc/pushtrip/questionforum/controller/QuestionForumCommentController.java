package com.abc.pushtrip.questionforum.controller;

import com.abc.pushtrip.questionforum.entity.QuestionForumComment;
import com.abc.pushtrip.questionforum.service.QuestionForumCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@SpringBootApplication
@Controller
public class QuestionForumCommentController {

    @Autowired
    private QuestionForumCommentService questionForumCommentService;

    // Select All 댓글 읽어오기
    @GetMapping("/boardQuestionComment")
    public String questionforumCommentSelectAll(@RequestParam("questionId") String questionId, Model model) {

        List<QuestionForumComment> qList = questionForumCommentService.questionForumCommentSelectAll(questionId);
        model.addAttribute("qList", qList);
        return "thymeleaf/board_question_comment";
    }

    // Insert 댓글 작성
    @PostMapping("/questionForumCommentInsert")
    public String questionForumCommentInsert(QuestionForumComment questionForumComment) {
        questionForumCommentService.questionForumCommentInsert(questionForumComment);
        return "redirect:/questionForumSelect?questionId=" + questionForumComment.getQuestionId();
    }

    @PostMapping("/questionForumCommentDelete")
    @ResponseBody
    public ResponseEntity<String> questionForumCommentDelete(@RequestParam("questionCommId") Long questionCommId) {
        try {
            questionForumCommentService.questionForumCommentDelete(questionCommId);
            return ResponseEntity.ok("Comment deleted!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting comment");
        }
    }
}
