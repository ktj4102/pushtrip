package com.abc.pushtrip.travelPlanner.repository;

import com.abc.pushtrip.travelPlanner.entity.TravelPlanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TravelPlannerRepository extends JpaRepository<TravelPlanner, Long> {

    // 유저 본인의 여행 일정 조회
    @Query("SELECT p FROM TravelPlanner p WHERE p.userId = :userId AND p.deleteYn = 'N' ORDER BY p.planId ASC")
    List<TravelPlanner> findAllByUserId(String userId);

    // 일정유형으로 검색
    List<TravelPlanner> findByUserIdAndPlanTypeContainingIgnoreCaseAndDeleteYn(String userId, String planType, String deleteYn);

}
