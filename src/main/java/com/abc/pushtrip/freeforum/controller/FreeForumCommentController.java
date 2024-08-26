package com.abc.pushtrip.freeforum.controller;

import com.abc.pushtrip.freeforum.service.FreeForumCommentService;
import com.abc.pushtrip.freeforum.entity.FreeForumComment;
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
public class FreeForumCommentController {

    @Autowired
    private FreeForumCommentService freeForumCommentService;

    // Select All 댓글 읽어오기
    @GetMapping("/boardFreeComment")
    public String freeForumCommentSelectAll(@RequestParam("freeId") String freeId, Model model) {
        List<FreeForumComment> cList = freeForumCommentService.freeForumCommentSelectAll(freeId);
        model.addAttribute("cList", cList);
        return "thymeleaf/board_free_comment";
    }

    // Insert 댓글 작성
    @PostMapping("/freeForumCommentInsert")
    public String freeForumCommentInsert(FreeForumComment freeForumComment) {
        freeForumCommentService.freeForumCommentInsert(freeForumComment);
        return "redirect:/freeForumSelect?freeId=" + freeForumComment.getFreeId();
    }

    // Delete 댓글 삭제
    @PostMapping("/freeForumCommentDelete")
    @ResponseBody
    public ResponseEntity<String> freeForumCommentDelete(@RequestParam("freeCommId") Long freeCommId) {
        try {
            freeForumCommentService.freeForumCommentDelete(freeCommId);
            return ResponseEntity.ok("Comment deleted!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting comment");
        }
    }
}
