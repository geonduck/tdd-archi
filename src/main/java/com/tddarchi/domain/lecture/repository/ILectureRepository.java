package com.tddarchi.domain.lecture.repository;

import com.tddarchi.domain.lecture.entity.Lecture;

public interface ILectureRepository {
    Lecture findById(long id);

    void save(Lecture lecture);
}
