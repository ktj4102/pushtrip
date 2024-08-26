package com.abc.pushtrip.travelPlanner.service;

import com.abc.pushtrip.travelPlanner.entity.TravelPlanner;

import java.util.List;

public interface TravelPlannerService {

    // Paging Select All
    List<TravelPlanner> travelPlannerSelectAll(String userId);

    // Insert
    void travelPlannerInsert(TravelPlanner travelPlanner);

//    // Select
//    TravelPlanner travelPlannerSelect(TravelPlanner travelPlanner);
//    TravelPlanner getTravelPlannerById(Long planId);
//
//    // Update
//    void travelPlannerUpdate(Long planId, TravelPlanner travelPlanner);

    // Delete
    void travelPlannerDelete(Long planId);

    //saerch
    List<TravelPlanner> findPlansByUserIdAndSearchTerm(String userId, String searchTerm);
}
