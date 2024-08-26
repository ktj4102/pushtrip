package com.abc.pushtrip.travelforum.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@NoArgsConstructor
@Builder

// 추가된 코드 핫플레이스 - 호기
public class TravelForumSummary {
    private Long travelId;
    private String title;
    private String content;
    private byte[] pictureFile;
    private Long likeCount;

    // 생성자
    public TravelForumSummary(Long travelId, String title, String content, byte[] pictureFile, Long likeCount) {
        this.travelId = travelId;
        this.title = title;
        this.content = content;
        this.pictureFile = pictureFile;
        this.likeCount = likeCount;
    }

    public String getPictureFileAsBase64() {
        if (pictureFile != null && pictureFile.length > 0) {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(pictureFile);
        }
        return null;
    }
}