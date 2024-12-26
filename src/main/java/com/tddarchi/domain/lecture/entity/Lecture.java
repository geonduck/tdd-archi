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
public class Lecture extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lectureName;      // 강의 이름
    private String lecturerInfo;     // 강연자 정보
    private Integer maxParticipants; // 수강 인원 제한
    private String summary;          // 강의 요약 내용

    private LocalDateTime enrollmentStart; // 수강 신청 시작일
    private LocalDateTime enrollmentEnd;   // 수강 신청 마감일
    private LocalDateTime lectureStart;    // 강의 시작일


}
