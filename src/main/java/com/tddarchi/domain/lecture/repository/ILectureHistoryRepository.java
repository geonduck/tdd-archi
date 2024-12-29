package com.tddarchi.domain.lecture.repository;

import com.tddarchi.domain.lecture.entity.LectureHistory;
import com.tddarchi.domain.lecture.enums.LectureStatus;

import java.util.List;

public interface ILectureHistoryRepository {
    void save(LectureHistory history);

    LectureHistory findByLectureIdAndUserId(Long lectureId, Long userId);

    List<LectureHistory> findByLectureId(Long lectureId);

    List<LectureHistory> findByUserIdAndStatus(Long userId, LectureStatus status);
}
