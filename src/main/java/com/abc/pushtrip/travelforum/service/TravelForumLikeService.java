package com.abc.pushtrip.travelforum.service;

public interface TravelForumLikeService {
    boolean addLike(Long travelId, String userId);
    long getLikeCount(Long travelId);
}
