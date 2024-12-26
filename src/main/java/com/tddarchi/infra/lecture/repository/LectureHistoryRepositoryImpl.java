package com.tddarchi.infra.lecture.repository;

import com.tddarchi.domain.lecture.entity.LectureHistory;
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
    public LectureHistory findByLectureIdAndUserId(LectureHistory history) {
        return jpaRepository.findByLectureIdAndUserId(history.getLectureId(), history.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("LectureApplication not found"));
    }

    @Override
    public List<LectureHistory> findByLectureId(LectureHistory history) {
        return jpaRepository.findByLectureId(history.getLectureId());
    }
}
