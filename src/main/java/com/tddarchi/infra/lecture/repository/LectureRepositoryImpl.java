package com.tddarchi.infra.lecture.repository;

import com.tddarchi.domain.lecture.entity.Lecture;
import com.tddarchi.domain.lecture.repository.ILectureRepository;
import com.tddarchi.infra.lecture.JpaRepository.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements ILectureRepository {

    private final LectureJpaRepository jpaRepository;

    @Override
    public Lecture findById(long id) {
        return jpaRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Lecture save(Lecture lecture) {
        jpaRepository.save(lecture);
        return lecture;
    }

    @Override
    public List<Lecture> findLecturesByDate(LocalDateTime searchDate) {
        return jpaRepository.findLecturesByDate(searchDate);
    }

    @Override
    public Lecture findByIdWithPessimisticLock(Long lectureId) {
        return jpaRepository.findByIdWithPessimisticLock(lectureId);
    }
}
