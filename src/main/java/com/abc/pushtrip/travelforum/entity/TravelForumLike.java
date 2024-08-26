package com.abc.pushtrip.travelforum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRAVEL_FORUM_LIKE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelForumLike {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "travel_forum_like_seq_gen")
    @SequenceGenerator(name = "travel_forum_like_seq_gen", sequenceName = "TRAVEL_FORUM_LIKE_SEQ", allocationSize = 1)
    @Column(name = "TRAVELLIKEID", nullable = false)
    private Long travelLikeId;

    //핫플 조인때문에 타입 바꿈 이거에 맞게 나머지 바꾸면 될듯 얼마 안 걸리더라
    @Column(name = "TRAVELID", nullable = false)
    private Long travelId;

    @Column(name = "USERID", nullable = false, length = 50)
    private String userId;
}
