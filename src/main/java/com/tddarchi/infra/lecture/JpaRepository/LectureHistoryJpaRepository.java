package com.tddarchi.infra.lecture.JpaRepository;

import com.tddarchi.domain.lecture.entity.LectureHistory;
import com.tddarchi.domain.lecture.enums.LectureStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureHistoryJpaRepository extends JpaRepository<LectureHistory, Long> {
    Optional<LectureHistory> findByLectureIdAndUserId(Long lectureId, Long userId);

    List<LectureHistory> findByLectureId(Long lectureId);

    List<LectureHistory> findByUserIdAndStatus(Long userId, LectureStatus status);

}
