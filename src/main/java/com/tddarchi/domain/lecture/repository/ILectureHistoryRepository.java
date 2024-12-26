package com.tddarchi.domain.lecture.repository;

import com.tddarchi.domain.lecture.entity.LectureHistory;

import java.util.List;

public interface ILectureHistoryRepository {
    void save(LectureHistory history);

    LectureHistory findByLectureIdAndUserId(LectureHistory history);

    List<LectureHistory> findByLectureId(LectureHistory history);
}
