package com.tddarchi.interfaces.lecture.dto;

import com.tddarchi.domain.lecture.entity.LectureHistory;
import com.tddarchi.domain.lecture.enums.LectureStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class LectureHistoryResponse {
    private Long lectureId;
    private String lectureName;
    private String lecturerInfo;
    private LectureStatus status;
    private LocalDateTime appliedAt;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static LectureHistoryResponse from(LectureHistory history) {
        return LectureHistoryResponse.builder()
                .lectureId(history.getLectureId())
                .lectureName(history.getLectureName())
                .lecturerInfo(history.getLecturerInfo())
                .status(history.getStatus())
                .createdDate(history.getCreatedDate())
                .build();
    }
}