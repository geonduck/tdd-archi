package com.tddarchi.interfaces.lecture.dto;

import com.tddarchi.domain.lecture.dto.LectureResult;
import com.tddarchi.domain.lecture.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class LectureResponse {
    private Long id;
    private String lectureName;
    private String lecturerInfo;
    private String summary;
    private int maxParticipants;
    private LocalDateTime enrollmentStart;
    private LocalDateTime enrollmentEnd;
    private LocalDateTime lectureStart;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static LectureResponse from(Lecture lecture) {
        return LectureResponse.builder()
                .id(lecture.getId())
                .lectureName(lecture.getLectureName())
                .lecturerInfo(lecture.getLecturerInfo())
                .summary(lecture.getSummary())
                .maxParticipants(lecture.getMaxParticipants())
                .enrollmentStart(lecture.getEnrollmentStart())
                .enrollmentEnd(lecture.getEnrollmentEnd())
                .lectureStart(lecture.getLectureStart())
                .build();
    }

    public static LectureResponse from(LectureResult result) {
        return LectureResponse.builder()
                .id(result.getLectureId())
                .lectureName(result.getLectureName())
                .build();
    }
}