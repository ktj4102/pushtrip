package com.abc.pushtrip.notice.repository;

import com.abc.pushtrip.notice.entity.NoticeForum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeForumRepository extends JpaRepository<NoticeForum, Long> {


    // @Query를 사용한 JPQL
    @Query("SELECT n FROM NoticeForum n WHERE n.deleteYn = 'N' ORDER BY n.noticeId DESC")
    Page<NoticeForum> findAll(Pageable pageable);
    // 게시글 제목, 작성자로 검색
    @Query("SELECT n FROM NoticeForum n WHERE n.deleteYn = 'N' AND ("
            + "(:searchCategory = 'title' AND n.title LIKE %:searchTerm%) OR "
            + "(:searchCategory = 'content' AND n.content LIKE %:searchTerm%)) "
            + "ORDER BY n.noticeId DESC")
    Page<NoticeForum> search(@Param("searchCategory") String searchCategory,
                               @Param("searchTerm") String searchTerm,
                               Pageable pageable);


}
