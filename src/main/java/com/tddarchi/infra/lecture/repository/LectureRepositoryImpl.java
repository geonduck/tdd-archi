package com.tddarchi.infra.lecture.repository;

import com.tddarchi.domain.lecture.entity.Lecture;
import com.tddarchi.domain.lecture.repository.ILectureRepository;
import com.tddarchi.infra.lecture.JpaRepository.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements ILectureRepository {

    private final LectureJpaRepository jpaRepository;

    @Override
    public Lecture findById(long id) {
        return jpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found"));
    }

    @Override
    public void save(Lecture lecture) {
        jpaRepository.save(lecture);
    }
}
