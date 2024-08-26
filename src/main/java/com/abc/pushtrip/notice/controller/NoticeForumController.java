package com.abc.pushtrip.notice.controller;


import com.abc.pushtrip.notice.entity.NoticeForum;
import com.abc.pushtrip.notice.service.NoticeForumService;
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
public class NoticeForumController {

    @Autowired
    private NoticeForumService noticeForumService;

    // Select All 을 거쳐서 와야 게시글이 뜸
    @GetMapping("/board_notice")
    public String board_notice() {
        return "thymeleaf/notice";
    }

    // Insert Form
    @GetMapping("board_notice_content_write")
    public String insertForm(Model model) {
        model.addAttribute("noticeForumInsert", new NoticeForum());
        return "thymeleaf/board_notice_content_write";
    }

    // Select Detail
    @GetMapping("/board_notice_content")
    public String selectDetail(Model model){
        return "thymeleaf/board_notice_content";
    }

    // Update Form
    @GetMapping("/board_notice_update")
    public String updateForm(@RequestParam("noticeId") Long noticeId, Model model){
        NoticeForum noticeForum = noticeForumService.getNoticeForumById(noticeId);
        model.addAttribute("noticeForum", noticeForum);
        return "thymeleaf/board_notice_update";
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Select All
    @GetMapping("/noticeForumSelectAll")
    public String noticeForumSelectAll(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<NoticeForum> paging = noticeForumService.selectAll(page);
        model.addAttribute("paging", paging);
        return "thymeleaf/board_notice";
    }

    // Search
    @GetMapping("/noticeForumSearch")
    public String searchPosts(Model model,
                              @RequestParam(value="searchCategory", defaultValue="title") String searchCategory,
                              @RequestParam(value="searchTerm", defaultValue="") String searchTerm,
                              @RequestParam(value="page", defaultValue="0") int page) {
        Page<NoticeForum> paging = noticeForumService.search(searchCategory, searchTerm, page);
        model.addAttribute("paging", paging);
        return "thymeleaf/board_notice";
    }

    // Insert
    @PostMapping("/noticeForumInsert")
    public String noticeForumInsert(@ModelAttribute("noticeForumInsert") NoticeForum noticeForum, BindingResult result, @RequestParam("image") MultipartFile image) {
        if (result.hasErrors()) {
            return "thymeleaf/insertForm";
        }
        noticeForumService.noticeForumInsert(noticeForum);
        try {
            noticeForumService.saveImage(noticeForum.getNoticeId(), image);
        } catch (IOException e) {
            // 이미지 저장 실패 처리
            e.printStackTrace();
        }
        return "redirect:/noticeForumSelectAll";
    }

    // Select
    @GetMapping("/noticeForumSelect")
    public String noticeForumSelect(@RequestParam("noticeId") Long noticeId, Model model) {
        NoticeForum noticeForum = new NoticeForum();
        noticeForum.setNoticeId(noticeId);
        NoticeForum selectedNoticeForum = noticeForumService.noticeForumSelect(noticeForum);
        model.addAttribute("noticeForum", selectedNoticeForum);
        return "thymeleaf/board_notice_content";
    }

    // Update
    @PostMapping("/noticeForumUpdate")
    public String noticeForumUpdate(@RequestParam("noticeId") Long noticeId,
                                  @ModelAttribute NoticeForum noticeForum,
                                  @RequestParam("image") MultipartFile image) throws IOException {
        noticeForumService.noticeForumUpdate(noticeId, noticeForum, image);
        return "redirect:/noticeForumSelectAll";
    }

    // Delete
    @PostMapping("/noticeForumDelete")
    public String noticeForumDelete(@RequestParam("noticeId") Long noticeId) {
        NoticeForum noticeForum = new NoticeForum();
        noticeForum.setNoticeId(noticeId);
        noticeForumService.noticeForumDelete(noticeId);
        return "redirect:/noticeForumSelectAll";
    }
}
