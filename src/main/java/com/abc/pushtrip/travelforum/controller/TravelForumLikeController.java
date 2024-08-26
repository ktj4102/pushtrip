package com.abc.pushtrip.travelforum.controller;

import com.abc.pushtrip.travelforum.service.TravelForumLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travelForumSelect")
public class TravelForumLikeController {

    @Autowired
    private TravelForumLikeService travelForumLikeService;

    // 좋아요 추가
    @PostMapping("/{travelId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long travelId, @RequestParam String userId) {
        boolean liked = travelForumLikeService.addLike(travelId, userId);
        if (liked) {
            return ResponseEntity.ok("Liked");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Already liked");
        }
    }

    // 좋아요 수 조회
    @GetMapping("/{travelId}/likeCount")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long travelId) {
        long count = travelForumLikeService.getLikeCount(travelId);
        return ResponseEntity.ok(count);
    }
}
