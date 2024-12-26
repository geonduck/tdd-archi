package com.tddarchi.infra.lecture.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tddarchi.domain.lecture.entity.Lecture;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
}
