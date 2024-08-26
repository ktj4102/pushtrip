package com.abc.pushtrip.questionforum.repository;

import com.abc.pushtrip.freeforum.entity.FreeForum;
import com.abc.pushtrip.questionforum.entity.QuestionForum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionForumRepository extends JpaRepository<QuestionForum, Long> {


    // @Query를 사용한 JPQL
    @Query("SELECT q FROM QuestionForum q WHERE q.deleteYn = 'N' ORDER BY q.questionId DESC")
    Page<QuestionForum> findAll(Pageable pageable);
    // 게시글 제목, 작성자로 검색
    @Query("SELECT q FROM QuestionForum q WHERE q.deleteYn = 'N' AND ("
            + "(:searchCategory = 'title' AND q.title LIKE %:searchTerm%) OR "
            + "(:searchCategory = 'userId' AND q.userId LIKE %:searchTerm%)) "
            + "ORDER BY q.questionId DESC")
    Page<QuestionForum> search(@Param("searchCategory") String searchCategory,
                           @Param("searchTerm") String searchTerm,
                           Pageable pageable);


}
