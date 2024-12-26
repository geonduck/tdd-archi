package com.tddarchi.infra.repository;

import com.tddarchi.domain.lecture.entity.LectureApplication;
import com.tddarchi.infra.lecture.repository.LectureApplicationRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LectureApplicationRepositoryTest {

    @Autowired
    private LectureApplicationRepositoryImpl lectureApplicationRepository;

    @Test
    @DisplayName("사용자 ID와 강의 ID로 신청 내역 찾기 성공")
    void findApplicationByLectureIdAndUserId_ShouldReturnApplication_WhenApplicationExists() {
        // given
        LectureApplication application = LectureApplication.builder()
                .lectureId(1L)
                .userId(1L)
                .appliedAt(LocalDateTime.of(2024, 12, 25, 15, 30))
                .build();

        lectureApplicationRepository.save(application);
        // when
        LectureApplication foundApplication = lectureApplicationRepository.findByLectureIdAndUserId(LectureApplication.builder()
                .lectureId(1L)
                .userId(1L).build());

        // then
        assertThat(foundApplication.getAppliedAt()).isEqualTo(LocalDateTime.of(2024, 12, 25, 15, 30));
    }

    @Test
    @DisplayName("강의 ID로 신청된 인원 수 반환 성공")
    void countApplicationsByLectureId_ShouldReturnNumberOfApplications_WhenApplicationsExist() {
        // given
        LectureApplication application = LectureApplication.builder()
                .lectureId(1L)
                .userId(1L)
                .appliedAt(LocalDateTime.of(2024, 12, 25, 15, 30))
                .build();

        lectureApplicationRepository.save(application);
        // when
        int applicationCount = lectureApplicationRepository.countByLectureId(1L);

        // then
        assertThat(applicationCount).isEqualTo(1);
    }

    @Test
    @DisplayName("사용자 ID와 강의 ID로 신청 내역 저장 성공")
    void saveApplicationByUserIdAndLectureId_ShouldSaveApplication_WhenValidInput() {
        // given
        LectureApplication newApplication = LectureApplication.builder()
                .lectureId(1L)
                .userId(1L)
                .appliedAt(LocalDateTime.of(2024, 12, 25, 15, 30))
                .build();

        lectureApplicationRepository.save(newApplication);

        // when
        LectureApplication savedApplication = lectureApplicationRepository.findByLectureIdAndUserId(newApplication);

        // then
        assertThat(savedApplication).isNotNull();
        assertThat(savedApplication.getUserId()).isEqualTo(1L);
        assertThat(savedApplication.getLectureId()).isEqualTo(1L);
    }
}