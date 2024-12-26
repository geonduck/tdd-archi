package com.tddarchi.infra.lecture.repository;

import com.tddarchi.domain.lecture.entity.LectureHistory;
import com.tddarchi.domain.lecture.enums.LectureStatus;
import com.tddarchi.domain.lecture.repository.ILectureHistoryRepository;
import com.tddarchi.infra.lecture.JpaRepository.LectureHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureHistoryRepositoryImpl implements ILectureHistoryRepository {

    private final LectureHistoryJpaRepository jpaRepository;

    @Override
    public void save(LectureHistory history) {
        jpaRepository.save(history);
    }

    @Override
    public LectureHistory findByLectureIdAndUserId(Long lectureId, Long userId) {
        return jpaRepository.findByLectureIdAndUserId(lectureId, userId)
                .orElse(null);
    }

    @Override
    public List<LectureHistory> findByLectureId(Long lectureId) {
        return jpaRepository.findByLectureId(lectureId);
    }

    @Override
    public List<LectureHistory> findByUserIdAndStatus(Long userId, LectureStatus status) {
        return jpaRepository.findByUserIdAndStatus(userId, status);
    }
}
