package com.abc.pushtrip.travelforum.controller;

import com.abc.pushtrip.travelforum.dto.TravelForumSummary;
import com.abc.pushtrip.travelforum.service.TravelForumService;
import com.abc.pushtrip.travelforum.entity.TravelForum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

@Controller
@SpringBootApplication
public class TravelForumController {

    @Autowired
    private TravelForumService travelForumService;

    // 메인화면
    // @GetMapping("/main")
    // public String main() {
    //     return "thymeleaf/main";
    // }

    // Insert Form
    @GetMapping("board_travel_content_write")
    public String insertForm(Model model) {
        model.addAttribute("travelForumInsert", new TravelForum());
        return "thymeleaf/board_travel_content_write";
    }

    // Select Detail
    @GetMapping("/board_travel_content")
    public String selectDetail(Model model){
        return "thymeleaf/board_travel_content";
    }

    // Update Form
    @GetMapping("/board_travel_update")
    public String updateForm(@RequestParam("travelId") Long travelId, Model model){
        TravelForum travelForum = travelForumService.getTravelForumById(travelId);
        model.addAttribute("travelForum", travelForum);
        return "thymeleaf/board_travel_update";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Select All
    @GetMapping("/travelForumSelectAll")
    public String travelForumSelectAll(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<TravelForum> paging = travelForumService.selectAll(page);
        model.addAttribute("paging", paging);
        return "thymeleaf/board_travel";
    }

    // Search
    @GetMapping("/travelForumSearch")
    public String searchPosts(Model model,
                              @RequestParam(value="searchCategory", defaultValue="title") String searchCategory,
                              @RequestParam(value="searchTerm", defaultValue="") String searchTerm,
                              @RequestParam(value="page", defaultValue="0") int page) {
        Page<TravelForum> paging = travelForumService.search(searchCategory, searchTerm, page);
        model.addAttribute("paging", paging);
        return "thymeleaf/board_travel";
    }

    // Insert
    @PostMapping("/travelForumInsert")
    public String travelForumInsert(@ModelAttribute("travelForumInsert") TravelForum travelForum, BindingResult result, @RequestParam("image") MultipartFile image) {
        if (result.hasErrors()) {
            return "thymeleaf/insertForm";
        }
        travelForumService.travelForumInsert(travelForum);
        try {
            travelForumService.saveImage(travelForum.getTravelId(), image);
        } catch (IOException e) {
            // 이미지 저장 실패 처리
            e.printStackTrace();
        }
        return "redirect:/travelForumSelectAll";
    }

    // Select
    @GetMapping("/travelForumSelect")
    public String travelForumSelect(@RequestParam("travelId") Long travelId, Model model) {
        TravelForum travelForum = new TravelForum();
        travelForum.setTravelId(travelId);
        TravelForum selectedTravelForum = travelForumService.travelForumSelect(travelForum);
        model.addAttribute("travelForum", selectedTravelForum);
        return "thymeleaf/board_travel_content";
    }

    // Update
    @PostMapping("/travelForumUpdate")
    public String travelForumUpdate(@RequestParam("travelId") Long travelId,
                                    @ModelAttribute TravelForum travelForum,
                                    @RequestParam("image") MultipartFile image) throws IOException {
        travelForumService.travelForumUpdate(travelId, travelForum, image);
        return "redirect:/travelForumSelectAll";
    }

    // Delete
    @PostMapping("/travelForumDelete")
    public String travelForumDelete(@RequestParam("travelId") Long travelId) {
        TravelForum travelForum = new TravelForum();
        travelForum.setTravelId(travelId);
        travelForumService.travelForumDelete(travelId);
        return "redirect:/travelForumSelectAll";
    }

    // 추가된 코드 핫플레이스 - 호기
    @GetMapping("/Top5")
    public String top5(Model model, TravelForumSummary travelForumSummary) {
        List<TravelForumSummary> paging = travelForumService.top5(travelForumSummary);
        model.addAttribute("top5", paging);
        return "thymeleaf/main_travel";
    }
}

