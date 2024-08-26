package com.abc.pushtrip.travelPlanner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "TRAVEL_PLANNER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelPlanner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "travel_planner_seq_gen")
    @SequenceGenerator(name = "travel_planner_seq_gen", sequenceName = "TRAVEL_PLANNER_SEQ", allocationSize = 1)
    @Column(name = "PLANID", nullable = false)
    private Long planId;

    @Column(name = "USERID", nullable = false, length = 50)
    private String userId;

    @Column(name = "PLANTYPE", nullable = false, length = 50)
    private String planType;

    @Column(name = "PLANDATE", nullable = false, length = 50)
    private String planDate;

    @Column(name = "PLANTIME", nullable = false, length = 50)
    private String planTime;

    @Column(name = "ROADADDR", nullable = false, length = 500)
    private String roadAddr;

    @Column(name = "JIBUNADDR", nullable = false, length = 500)
    private String jibunAddr;

    @Column(name = "PLANCOST", nullable = false, length = 50)
    private String planCost;

    @Column(name = "PLANMEMO", nullable = false, length = 50)
    private String planMemo;

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
