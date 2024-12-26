package com.tddarchi.domain.lecture.repository;

import com.tddarchi.domain.lecture.entity.LectureApplication;

public interface ILectureApplicationRepository {
    LectureApplication findByLectureIdAndUserId(Long lectureId, Long userId);

    int countByLectureId(Long lectureId);

    void save(LectureApplication newApplication);
}
