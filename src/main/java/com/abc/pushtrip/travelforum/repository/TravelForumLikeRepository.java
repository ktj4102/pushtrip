package com.abc.pushtrip.travelforum.repository;

import com.abc.pushtrip.travelforum.entity.TravelForumLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelForumLikeRepository extends JpaRepository<TravelForumLike, Long> {
    boolean existsByTravelIdAndUserId(Long travelId, String userId);
    long countByTravelId(Long travelId);
}
