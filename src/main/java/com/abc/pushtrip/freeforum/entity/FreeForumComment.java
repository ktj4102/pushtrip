package com.abc.pushtrip.freeforum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "FREE_FORUM_COMMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreeForumComment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "free_forum_comment_seq_gen")
    @SequenceGenerator(name = "free_forum_comment_seq_gen", sequenceName = "FREE_FORUM_COMMENT_SEQ", allocationSize = 1)
    @Column(name = "FREECOMMID", nullable = false)
    private Long freeCommId;

    @Column(name = "FREEID", nullable = false, length =50)
    private String freeId;

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
