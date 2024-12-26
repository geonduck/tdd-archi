package com.tddarchi.domain.lecture.dto;

import com.tddarchi.domain.lecture.entity.Lecture;
import com.tddarchi.domain.lecture.enums.LectureStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureResult {
    private final Long lectureId;
    private final String lectureName;
    private final LectureStatus status;
    private final String message;

    public static LectureResult success(Lecture lecture) {
        return LectureResult.builder()
                .lectureId(lecture.getId())
                .lectureName(lecture.getLectureName())
                .status(LectureStatus.SUCCESS)
                .message("수강신청이 완료되었습니다.")
                .build();
    }

    public static LectureResult fail(Lecture lecture, String errorMessage) {
        return LectureResult.builder()
                .lectureId(lecture.getId())
                .lectureName(lecture.getLectureName())
                .status(LectureStatus.FAILED)
                .message(errorMessage)
                .build();
    }
}