package com.tddarchi.domain.lecture.repository;

import com.tddarchi.domain.lecture.entity.Lecture;

import java.time.LocalDateTime;
import java.util.List;

public interface ILectureRepository {
    Lecture findById(long id);

    Lecture save(Lecture lecture);

    List<Lecture> findLecturesByDate(LocalDateTime searchDate);
}
