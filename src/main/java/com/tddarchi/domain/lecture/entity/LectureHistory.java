package com.tddarchi.domain.lecture.entity;

import com.tddarchi.domain.lecture.enums.LectureStatus;
import com.tddarchi.support.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class LectureHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lectureId;       // 강의 참조 ID
    private Long userId;          // 사용자 참조 ID
    private LectureStatus status; // 성공 여부

    // 강의 관련 정보 저장
    private String lectureName;   // 강의명
    private String lecturerInfo;  // 강연자 정보

    private LocalDateTime recordedAt;  // 기록 시간
}


