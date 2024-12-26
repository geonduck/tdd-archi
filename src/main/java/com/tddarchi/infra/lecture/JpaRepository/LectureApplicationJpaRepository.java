package com.tddarchi.infra.lecture.JpaRepository;

import com.tddarchi.domain.lecture.entity.LectureApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureApplicationJpaRepository extends JpaRepository<LectureApplication, Long> {
    Optional<LectureApplication> findByLectureIdAndUserId(Long lectureId, Long userId);
    int countByLectureId(long lectureId);
}
