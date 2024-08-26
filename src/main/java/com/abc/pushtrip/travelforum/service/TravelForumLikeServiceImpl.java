package com.abc.pushtrip.travelforum.service;

import com.abc.pushtrip.travelforum.entity.TravelForumLike;
import com.abc.pushtrip.travelforum.repository.TravelForumLikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TravelForumLikeServiceImpl implements TravelForumLikeService{

    @Autowired
    private TravelForumLikeRepository travelForumLikeRepository;

    @Override
    public boolean addLike(Long travelId, String userId) {
        // 사용자 좋아요 기록 확인
        if (travelForumLikeRepository.existsByTravelIdAndUserId(travelId, userId)) {
            return false; // 이미 좋아요를 눌렀으면
        }

        // 좋아요 기록 저장
        TravelForumLike like = TravelForumLike.builder()
                .travelId(travelId)
                .userId(userId)
                .build();
        travelForumLikeRepository.save(like);

        return true;
    }

    @Override
    public long getLikeCount(Long travelId) {
        return travelForumLikeRepository.countByTravelId(travelId);
    }
}
