package com.tddarchi.domain.lecture.repository;

import com.tddarchi.domain.lecture.entity.LectureApplication;

public interface ILectureApplicationRepository {
    LectureApplication findByLectureIdAndUserId(LectureApplication application);

    int countByLectureId(long lectureId);

    void save(LectureApplication newApplication);
}
