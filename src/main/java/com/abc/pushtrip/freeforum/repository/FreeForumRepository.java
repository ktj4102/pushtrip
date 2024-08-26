package com.abc.pushtrip.freeforum.repository;

import com.abc.pushtrip.freeforum.entity.FreeForum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface FreeForumRepository extends JpaRepository<FreeForum, Long> {

    @Query("SELECT f FROM FreeForum f WHERE f.deleteYn = 'N' ORDER BY f.freeId DESC")
    Page<FreeForum> findAll(Pageable pageable);

    // 게시글 제목, 작성자로 검색
    @Query("SELECT f FROM FreeForum f WHERE f.deleteYn = 'N' AND ("
            + "(:searchCategory = 'title' AND f.title LIKE %:searchTerm%) OR "
            + "(:searchCategory = 'userId' AND f.userId LIKE %:searchTerm%)) "
            + "ORDER BY f.freeId DESC")
    Page<FreeForum> search(@Param("searchCategory") String searchCategory,
                             @Param("searchTerm") String searchTerm,
                             Pageable pageable);
}
