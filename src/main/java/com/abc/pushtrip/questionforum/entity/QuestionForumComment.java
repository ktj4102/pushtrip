package com.abc.pushtrip.questionforum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Entity
@Table(name = "QUESTION_FORUM_COMMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionForumComment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_forum_comment_seq_gen")
    @SequenceGenerator(name = "question_forum_comment_seq_gen", sequenceName = "QUESTION_FORUM_COMMENT_SEQ", allocationSize = 1)
    @Column(name = "QUESTIONCOMMID", nullable = false)
    private Long questionCommId;

    @Column(name = "QUESTIONID", nullable = false, length =50)
    private String questionId;

    @Column(name = "USERID", nullable = false, length = 50)
    private String userId;

    @Column(name = "COMMENTTEXT", nullable = false, length = 500)
    private String commentText;

    @Builder.Default
    @Column(name = "DELETEYN", nullable = false, length = 1)
    private String deleteYn = "N";

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "INSERTDATE", nullable = false, updatable = false)
    private Date insertDate = new Date();

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "UPDATEDATE", nullable = false)
    private Date updateDate = new Date();


    @PreUpdate
    protected void onUpdate() {
        updateDate = new Date();
    }


}
