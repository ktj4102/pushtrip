package com.abc.pushtrip.travelforum.controller;

import com.abc.pushtrip.travelforum.service.TravelForumCommentService;
import com.abc.pushtrip.travelforum.entity.TravelForumComment;
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
public class TravelForumCommentController {

    @Autowired
    private TravelForumCommentService travelForumCommentService;

    // Select All 댓글 읽어오기
    @GetMapping("/boardTravelComment")
    public String travelForumCommentSelectAll(@RequestParam("travelId") String travelId, Model model) {
        List<TravelForumComment> cList = travelForumCommentService.travelForumCommentSelectAll(travelId);
        model.addAttribute("cList", cList);
        return "thymeleaf/board_travel_comment";
    }

    // Insert 댓글 작성
    @PostMapping("/travelForumCommentInsert")
    public String travelForumCommentInsert(TravelForumComment travelForumComment) {
        travelForumCommentService.travelForumCommentInsert(travelForumComment);
        return "redirect:/travelForumSelect?travelId=" + travelForumComment.getTravelId();
    }

    // Delete 댓글 삭제
    @PostMapping("/travelForumCommentDelete")
    @ResponseBody
    public ResponseEntity<String> travelForumCommentDelete(@RequestParam("travelCommId") Long travelCommId) {
        try {
            travelForumCommentService.travelForumCommentDelete(travelCommId);
            return ResponseEntity.ok("Comment deleted!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting comment");
        }
    }
}
