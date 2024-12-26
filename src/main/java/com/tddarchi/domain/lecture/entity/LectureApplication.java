package com.tddarchi.domain.lecture.entity;


import com.tddarchi.support.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class LectureApplication extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lectureId;  // 강의 참조 ID
    private Long userId;     // 사용자 참조 ID

    private LocalDateTime appliedAt;   // 신청 시간

}

