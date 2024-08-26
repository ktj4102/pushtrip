package com.abc.pushtrip.travelPlanner.service;

import com.abc.pushtrip.travelPlanner.entity.TravelPlanner;
import com.abc.pushtrip.travelPlanner.repository.TravelPlannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TravelPlannerServiceImpl implements TravelPlannerService {

    @Autowired
    private TravelPlannerRepository travelPlannerRepository;

    // Select All
    @Override
    public List<TravelPlanner> travelPlannerSelectAll(String userId) {
        System.out.println("TravelPlannerService Select All >>> : true");

        return travelPlannerRepository.findAllByUserId(userId);
    }

    // Insert
    @Override
    public void travelPlannerInsert(TravelPlanner travelPlanner) {
        System.out.println("TravelPlannerService Insert >>> : true");

        travelPlannerRepository.save(travelPlanner);
    }

    // Delete
    @Override
    public void travelPlannerDelete(Long planId) {
        System.out.println("TravelPlannerService Delete >>> true");

        // 엔티티가 데이터베이스에 존재하는지 확인
        Optional<TravelPlanner> existingTravelPlanner = travelPlannerRepository.findById(planId);

        if (existingTravelPlanner.isPresent()) {
            // 엔티티가 존재하면 deleteyn 값을 "Y"로 설정하고 저장
            TravelPlanner plan = existingTravelPlanner.get();
            plan.setDeleteYn("Y");
            travelPlannerRepository.save(plan);
        } else {
            // 엔티티가 존재하지 않는 경우 처리
            System.out.println("ID not found : " + planId);
        }
    }

//    @Override
//    public TravelPlanner travelPlannerSelect(TravelPlanner travelPlanner) {
//        return null;
//    }

//    @Override
//    public TravelPlanner getTravelPlannerById(Long planId) {
//        return null;
//    }

//    @Override
//    public void travelPlannerUpdate(Long planId, TravelPlanner travelPlanner) {
//
//    }

    @Override
    public List<TravelPlanner> findPlansByUserIdAndSearchTerm(String userId, String searchTerm) {
        return travelPlannerRepository.findByUserIdAndPlanTypeContainingIgnoreCaseAndDeleteYn(userId, searchTerm, "N");
    }
}
