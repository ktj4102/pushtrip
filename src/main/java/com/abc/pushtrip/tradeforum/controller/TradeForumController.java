package com.abc.pushtrip.tradeforum.controller;

import com.abc.pushtrip.tradeforum.entity.TradeForum;
import com.abc.pushtrip.tradeforum.service.TradeForumService;
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
import java.util.List;

@Controller
@SpringBootApplication
public class TradeForumController {

    @Autowired
    private TradeForumService tradeForumService;

    //메인화면
    @GetMapping("/main")
    public String main() {
        return "thymeleaf/main"; // Thymeleaf 템플릿 경로
    }

    //selectAll 을 거쳐서 와야 게시글이 뜬다
    @GetMapping("/board_trade")
    public String board_trade() {
        return "thymeleaf/board_trade"; // Thymeleaf 템플릿 경로
    }

    @GetMapping("/board_trade_content_write")
    public String insertForm(Model model) {
        model.addAttribute("tradeForumInsert", new TradeForum());
        return "thymeleaf/board_trade_content_write";
    }

    @GetMapping("/board_trade_content")
    public String selectDetail(Model model){
        return "thymeleaf/board_trade_content";
    }

    @GetMapping("/board_trade_update")
    public String updateForm(@RequestParam("tradeId") Long tradeId, Model model){
        TradeForum tradeForum = tradeForumService.getTradeForumById(tradeId);
        model.addAttribute("tradeForum", tradeForum);
        return "thymeleaf/board_trade_update";
    }

    // 페이지 이동
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // selectAll 기능
    @GetMapping("/tradeForumSelectAll")
    public String tradeForumSelectAll(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<TradeForum> paging = tradeForumService.selectAll(page);
        model.addAttribute("paging", paging);
        return "thymeleaf/board_trade";
    }

    // Search
    @GetMapping("/tradeForumSearch")
    public String searchPosts(Model model,
                              @RequestParam(value="searchCategory", defaultValue="title") String searchCategory,
                              @RequestParam(value="searchTerm", defaultValue="") String searchTerm,
                              @RequestParam(value="page", defaultValue="0") int page) {
        Page<TradeForum> paging = tradeForumService.search(searchCategory, searchTerm, page);
        model.addAttribute("paging", paging);
        return "thymeleaf/board_trade";
    }

    @PostMapping("/tradeForumInsert")
    public String tradeForumInsert(@ModelAttribute("tradeForumInsert") TradeForum tradeForum,
                                   BindingResult result, @RequestParam("image") MultipartFile image) {
        if (result.hasErrors()) {
            return "thymeleaf/insertForm";
        }
        tradeForumService.tradeForumInsert(tradeForum);
        try {
            tradeForumService.saveImage(tradeForum.getTradeId(), image);
        } catch (IOException e) {
            // 이미지 저장 실패 처리
            e.printStackTrace();
        }
        return "redirect:/tradeForumSelectAll";
    }

    //select 기능
    @GetMapping("/tradeForumSelect")
    public String tradeForumSelect(@RequestParam("tradeId") Long tradeId, Model model) {
        TradeForum tradeForum = new TradeForum();
        tradeForum.setTradeId(tradeId);
        TradeForum selectedTradeForum = tradeForumService.tradeForumSelect(tradeForum);
        model.addAttribute("tradeForum", selectedTradeForum);

        //조회수 순으로 가져오기
        List<TradeForum> topViewedTradeForums = tradeForumService.findTop6ByOrderByViewCountDesc();
        model.addAttribute("topViewedTradeForums", topViewedTradeForums);

        return "thymeleaf/board_trade_content";
    }


    @PostMapping("/tradeForumUpdate")
    public String tradeForumUpdate(@RequestParam("tradeId") Long tradeId,
                                   @ModelAttribute TradeForum tradeForum,
                                   @RequestParam("image") MultipartFile image) throws IOException {
        tradeForumService.tradeForumUpdate(tradeId, tradeForum, image);
        return "redirect:/tradeForumSelectAll";
    }

    // Delete
    @PostMapping("/tradeForumDelete")
    public String tradeForumDelete(@RequestParam("tradeId") Long tradeId) {
        TradeForum tradeForum = new TradeForum();
        tradeForum.setTradeId(tradeId);
        tradeForumService.tradeForumDelete(tradeId);
        return "redirect:/tradeForumSelectAll";
    }
}
