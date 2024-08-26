package com.abc.pushtrip.tradeforum.controller;

import com.abc.pushtrip.tradeforum.entity.TradeForumComment;
import com.abc.pushtrip.tradeforum.service.TradeForumCommentService;
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
public class TradeForumCommentController {

    @Autowired
    private TradeForumCommentService tradeForumCommentService;

    // Select All 댓글 읽어오기
    @GetMapping("/boardTradeComment")
    public String tradeForumCommentSelectAll(@RequestParam("tradeId") String tradeId, Model model) {
        List<TradeForumComment> cList = tradeForumCommentService.tradeForumCommentSelectAll(tradeId);
        model.addAttribute("cList", cList);
        return "thymeleaf/board_trade_comment";
    }

    // Insert 댓글 작성
    @PostMapping("/tradeForumCommentInsert")
    public String tradeForumCommentInsert(TradeForumComment tradeForumComment) {
        tradeForumCommentService.tradeForumCommentInsert(tradeForumComment);
        return "redirect:/tradeForumSelect?tradeId=" + tradeForumComment.getTradeId();
    }

    @PostMapping("/tradeForumCommentDelete")
    @ResponseBody
    public ResponseEntity<String> tradeForumCommentDelete(@RequestParam("tradeCommId") Long tradeCommId) {
        try {
            tradeForumCommentService.tradeForumCommentDelete(tradeCommId);
            return ResponseEntity.ok("Comment deleted!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting comment");
        }
    }
}
