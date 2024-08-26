package com.abc.pushtrip.travelforum.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Entity
@Table(name = "TRAVEL_FORUM_COMMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelForumComment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "travel_forum_comment_seq_gen")
    @SequenceGenerator(name = "travel_forum_comment_seq_gen", sequenceName = "TRAVEL_FORUM_COMMENT_SEQ", allocationSize = 1)
    @Column(name = "TRAVELCOMMID", nullable = false)
    private Long travelCommId;

    @Column(name = "TRAVELID", nullable = false, length =50)
    private String travelId;

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
