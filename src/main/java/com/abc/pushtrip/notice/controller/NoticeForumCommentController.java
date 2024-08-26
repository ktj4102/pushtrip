package com.abc.pushtrip.notice.controller;

import com.abc.pushtrip.notice.entity.NoticeForumComment;
import com.abc.pushtrip.notice.service.NoticeForumCommentService;
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
public class NoticeForumCommentController {

    @Autowired
    private NoticeForumCommentService noticeForumCommentService;

    // Select All 댓글 읽어오기
    @GetMapping("/boardNoticeComment")
    public String noticeforumCommentSelectAll(@RequestParam("noticeId") String noticeId, Model model) {

        List<NoticeForumComment> nList = noticeForumCommentService.noticeForumCommentSelectAll(noticeId);
        model.addAttribute("nList", nList);
        return "thymeleaf/board_notice_comment";
    }

    // Insert 댓글 작성
    @PostMapping("/noticeForumCommentInsert")
    public String noticeForumCommentInsert(NoticeForumComment noticeForumComment) {
        noticeForumCommentService.noticeForumCommentInsert(noticeForumComment);
        return "redirect:/noticeForumSelect?noticeId=" + noticeForumComment.getNoticeId();
    }

    @PostMapping("/noticeForumCommentDelete")
    @ResponseBody
    public ResponseEntity<String> noticeForumCommentDelete(@RequestParam("noticeCommId") Long noticeCommId) {
        try {
            noticeForumCommentService.noticeForumCommentDelete(noticeCommId);
            return ResponseEntity.ok("Comment deleted!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting comment");
        }
    }
}
