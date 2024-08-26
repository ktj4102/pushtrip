package com.abc.pushtrip.travelforum.repository;

import com.abc.pushtrip.travelforum.dto.TravelForumSummary;
import com.abc.pushtrip.travelforum.entity.TravelForum;
import com.abc.pushtrip.travelforum.entity.TravelForumLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TravelForumRepository extends JpaRepository<TravelForum, Long> {

    @Query("SELECT t FROM TravelForum t WHERE t.deleteYn = 'N' ORDER BY t.travelId DESC")
    Page<TravelForum> findAll(Pageable pageable);

    // 게시글 제목, 작성자로 검색
    @Query("SELECT t FROM TravelForum t WHERE t.deleteYn = 'N' AND ("
            + "(:searchCategory = 'title' AND t.title LIKE %:searchTerm%) OR "
            + "(:searchCategory = 'userId' AND t.userId LIKE %:searchTerm%)) "
            + "ORDER BY t.travelId DESC")
    Page<TravelForum> search(@Param("searchCategory") String searchCategory,
                             @Param("searchTerm") String searchTerm,
                             Pageable pageable);

    // 추가된 코드 핫플레이스 - 호기
    @Query("SELECT new com.abc.pushtrip.travelforum.dto.TravelForumSummary(" +
            "f.travelId, f.title, f.content, f.pictureFile, " +
            "(SELECT COALESCE(COUNT(l.id), 0) FROM TravelForumLike l WHERE l.travelId = f.travelId)) " +
            "FROM TravelForum f WHERE f.deleteYn = 'N' " +
            "ORDER BY (SELECT COALESCE(COUNT(l.id), 0) FROM TravelForumLike l WHERE l.travelId = f.travelId) DESC")
    List<TravelForumSummary> findTop5TravelForumSummaries(Pageable pageable);
}


