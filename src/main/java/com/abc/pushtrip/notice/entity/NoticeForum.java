package com.abc.pushtrip.notice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Base64;
import java.util.Date;

@Entity
@Table(name = "NOTICE_FORUM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeForum {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_forum_seq_gen")
    @SequenceGenerator(name = "notice_forum_seq_gen", sequenceName = "NOTICE_FORUM_SEQ", allocationSize = 1)
    @Column(name = "NOTICEID", nullable = false)
    private Long noticeId;

    @Column(name = "USERID", nullable = false, length = 50)
    private String userId;

    @Column(name = "TITLE", nullable = false, length = 50)
    private String title;

    @Column(name = "CONTENT", nullable = false, length = 500)
    private String content;

    @Lob
    @Column(name = "PICTUREFILE")
    private byte[] pictureFile;

    @Builder.Default
    @Column(name = "VIEW_COUNT", nullable = false)
    private int viewCount = 0;

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

    public String getPictureFileAsBase64() {
        if (pictureFile != null && pictureFile.length > 0) {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(pictureFile);
        }
        return null;
    }


}
