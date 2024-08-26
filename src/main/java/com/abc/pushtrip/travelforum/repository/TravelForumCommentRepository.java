package com.abc.pushtrip.travelforum.repository;

import com.abc.pushtrip.travelforum.entity.TravelForumComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TravelForumCommentRepository extends JpaRepository<TravelForumComment, Long> {

    // 게시물 travelId로 댓글 전체 조회
    @Query("SELECT c FROM TravelForumComment c WHERE c.travelId = :travelId AND c.deleteYn = 'N' ORDER BY c.travelCommId DESC")
    List<TravelForumComment> findActiveTravelForumCommentByTravelId(String travelId);

}
