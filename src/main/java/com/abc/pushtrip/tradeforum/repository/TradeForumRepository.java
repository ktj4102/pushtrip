package com.abc.pushtrip.tradeforum.repository;

import com.abc.pushtrip.tradeforum.entity.TradeForum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TradeForumRepository extends JpaRepository<TradeForum, Long> {
    @Query("SELECT t FROM TradeForum t WHERE t.deleteYn = 'N' ORDER BY t.tradeId DESC")
    Page<TradeForum> findAll(Pageable pageable);

    // 게시글 제목, 작성자로 검색
    @Query("SELECT t FROM TradeForum t WHERE t.deleteYn = 'N' AND ("
            + "(:searchCategory = 'title' AND t.title LIKE %:searchTerm%) OR "
            + "(:searchCategory = 'userId' AND t.userId LIKE %:searchTerm%)) "
            + "ORDER BY t.tradeId DESC")
    Page<TradeForum> search(@Param("searchCategory") String searchCategory,
                             @Param("searchTerm") String searchTerm,
                             Pageable pageable);

    List<TradeForum> findTop6ByOrderByViewCountDesc();
}
