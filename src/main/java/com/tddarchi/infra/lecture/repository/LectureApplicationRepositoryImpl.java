package com.tddarchi.infra.lecture.repository;

import com.tddarchi.domain.lecture.entity.LectureApplication;
import com.tddarchi.domain.lecture.repository.ILectureApplicationRepository;
import com.tddarchi.infra.lecture.JpaRepository.LectureApplicationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LectureApplicationRepositoryImpl implements ILectureApplicationRepository {

    private final LectureApplicationJpaRepository jpaRepository;

    @Override
    public LectureApplication findByLectureIdAndUserId(LectureApplication application) {
        return jpaRepository.findByLectureIdAndUserId(application.getLectureId(), application.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("LectureApplication not found"));
    }

    @Override
    public int countByLectureId(long lectureId) {
        return jpaRepository.countByLectureId(lectureId);
    }

    @Override
    public void save(LectureApplication newApplication) {
        jpaRepository.save(newApplication);
    }

}
