package com.abc.pushtrip.freeforum.controller;


import com.abc.pushtrip.freeforum.service.FreeForumService;
import com.abc.pushtrip.freeforum.entity.FreeForum;
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
public class FreeForumController {

    @Autowired
    private FreeForumService freeForumService;

    // Select All 을 거쳐서 와야 게시글이 뜸
    @GetMapping("/board_free")
    public String board_free() {
        return "thymeleaf/board_free";
    }

    // Insert Form
    @GetMapping("board_free_content_write")
    public String insertForm(Model model) {
        model.addAttribute("freeForumInsert", new FreeForum());
        return "thymeleaf/board_free_content_write";
    }

    // Select Detail
    @GetMapping("/board_free_content")
    public String selectDetail(Model model){
        return "thymeleaf/board_free_content";
    }

    // Update Form
    @GetMapping("/board_free_update")
    public String updateForm(@RequestParam("freeId") Long freeId, Model model){
        FreeForum freeForum = freeForumService.getFreeForumById(freeId);
        model.addAttribute("freeForum", freeForum);
        return "thymeleaf/board_free_update";
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Select All
    @GetMapping("/freeForumSelectAll")
    public String freeForumSelectAll(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<FreeForum> paging = freeForumService.selectAll(page);
        model.addAttribute("paging", paging);
        return "thymeleaf/board_free";
    }

    // Search
    @GetMapping("/freeForumSearch")
    public String searchPosts(Model model,
                              @RequestParam(value="searchCategory", defaultValue="title") String searchCategory,
                              @RequestParam(value="searchTerm", defaultValue="") String searchTerm,
                              @RequestParam(value="page", defaultValue="0") int page) {
        Page<FreeForum> paging = freeForumService.search(searchCategory, searchTerm, page);
        model.addAttribute("paging", paging);
        return "thymeleaf/board_free";
    }

    // Insert
    @PostMapping("/freeForumInsert")
    public String freeForumInsert(@ModelAttribute("freeForumInsert") FreeForum freeForum, BindingResult result, @RequestParam("image") MultipartFile image) {
        if (result.hasErrors()) {
            return "thymeleaf/insertForm";
        }
        freeForumService.freeForumInsert(freeForum);
        try {
            freeForumService.saveImage(freeForum.getFreeId(), image);
        } catch (IOException e) {
            // 이미지 저장 실패 처리
            e.printStackTrace();
        }
        return "redirect:/freeForumSelectAll";
    }

    // Select
    @GetMapping("/freeForumSelect")
    public String freeForumSelect(@RequestParam("freeId") Long freeId, Model model) {
        FreeForum freeForum = new FreeForum();
        freeForum.setFreeId(freeId);
        FreeForum selectedFreeForum = freeForumService.freeForumSelect(freeForum);
        model.addAttribute("freeForum", selectedFreeForum);
        return "thymeleaf/board_free_content";
    }

    // Update
    @PostMapping("/freeForumUpdate")
    public String freeForumUpdate(@RequestParam("freeId") Long freeId,
                                    @ModelAttribute FreeForum freeForum,
                                    @RequestParam("image") MultipartFile image) throws IOException {
        freeForumService.freeForumUpdate(freeId, freeForum, image);
        return "redirect:/freeForumSelectAll";
    }

    // Delete
    @PostMapping("/freeForumDelete")
    public String freeForumDelete(@RequestParam("freeId") Long freeId) {
        FreeForum freeForum = new FreeForum();
        freeForum.setFreeId(freeId);
        freeForumService.freeForumDelete(freeId);
        return "redirect:/freeForumSelectAll";
    }
}
