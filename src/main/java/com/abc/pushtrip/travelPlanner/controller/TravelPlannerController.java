package com.abc.pushtrip.travelPlanner.controller;

import com.abc.pushtrip.travelPlanner.entity.TravelPlanner;
import com.abc.pushtrip.travelPlanner.service.TravelPlannerService;
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

@Controller
@SpringBootApplication
public class TravelPlannerController {

    @Autowired
    private TravelPlannerService travelPlannerService;

    // 일정공유 메인화면
    @GetMapping("/travelPlannerTest")
    public String main() {
        return "thymeleaf/travel_planner_test"; // Thymeleaf 템플릿 경로
    }

//    // Select All Plan by userID
//    @GetMapping("/travelPlannerSelectAll")
//    @ResponseBody
//    public String travelPlannerSelectAll(@RequestParam("userId") String userId, Model model) {
//        List<TravelPlanner> pList = travelPlannerService.travelPlannerSelectAll(userId);
//        model.addAttribute("pList", pList);
//        return "thymeleaf/travel_planner_test";
//    }

    // Select All Plan by userID
    @GetMapping("/travelPlannerSelectAll")
    @ResponseBody
    public List<TravelPlanner> travelPlannerSelectAll(@RequestParam("userId") String userId) {
        return travelPlannerService.travelPlannerSelectAll(userId);
    }

    // Insert Plan
    @PostMapping("/travelPlannerInsert")
    public String travelPlannerInsert(TravelPlanner travelPlanner){
        travelPlannerService.travelPlannerInsert(travelPlanner);
        return "redirect:/travelPlannerTest";
    }

    // Delete Plan
    @PostMapping("/travelPlannerDelete")
    @ResponseBody
    public ResponseEntity<String> travelPlannerDelete(@RequestParam("planId") Long planId){
        try{
            travelPlannerService.travelPlannerDelete(planId);
            return ResponseEntity.ok("Plan deleted!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Deleting Plan");
        }
    }
    // Search Plan
    @GetMapping("/planSearch")
    public String searchPlans(@RequestParam(name = "searchTerm", required = false, defaultValue = "") String searchTerm,
                              @RequestParam(name = "userId", required = true) String userId,
                              Model model) {
        // 사용자 ID와 검색어로 일정을 검색합니다 (DELETEYN이 'N'인 것만)
        List<TravelPlanner> plans = travelPlannerService.findPlansByUserIdAndSearchTerm(userId, searchTerm);

        // 검색 결과를 모델에 추가
        model.addAttribute("plans", plans);

        // 검색 결과를 보여줄 뷰의 이름을 반환합니다
        return "thymeleaf/mypage_plan";
    }

}
