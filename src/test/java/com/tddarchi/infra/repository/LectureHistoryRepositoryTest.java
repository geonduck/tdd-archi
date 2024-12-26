package com.tddarchi.infra.repository;

import com.tddarchi.domain.lecture.entity.LectureApplication;
import com.tddarchi.domain.lecture.entity.LectureHistory;
import com.tddarchi.domain.lecture.enums.LectureStatus;
import com.tddarchi.infra.lecture.repository.LectureHistoryRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LectureHistoryRepositoryTest {

    @Autowired
    LectureHistoryRepositoryImpl lectureHistoryRepository;

    @Test
    @DisplayName("신청 내역 저장")
    void saveLectureHistory_ShouldSaveSuccessfully_WhenValidDataProvided() {

        // given
        LectureApplication application = LectureApplication.builder()
                .lectureId(1L)
                .userId(100L)
                .build();

        LectureHistory expectedHistory = LectureHistory.builder()
                .lectureId(application.getLectureId())
                .userId(application.getUserId())
                .lectureName("Test Lecture")
                .lecturerInfo("Duckeeee")
                .status(LectureStatus.SUCCESS)
                .build();

        // when
        lectureHistoryRepository.save(expectedHistory);

        // then
        LectureHistory savedHistory = lectureHistoryRepository.findByLectureIdAndUserId(expectedHistory);

        assertThat(savedHistory).isNotNull();
        assertThat(savedHistory.getLectureName()).isEqualTo("Test Lecture");
        assertThat(savedHistory.getLecturerInfo()).isEqualTo("Duckeeee");
        assertThat(savedHistory.getStatus()).isEqualTo(LectureStatus.SUCCESS);

    }


    @Test
    @DisplayName("강의 ID 로 신청 내역 조회 성공")
    void getLectureHistoryById_ShouldReturnLectureHistory_WhenHistoryExists() {

        // given
        LectureApplication application = LectureApplication.builder()
                .lectureId(1L)
                .userId(100L)
                .build();

        LectureHistory expectedHistory = LectureHistory.builder()
                .lectureId(application.getLectureId())
                .userId(application.getUserId())
                .lectureName("Test Lecture")
                .lecturerInfo("Duckeeee")
                .status(LectureStatus.SUCCESS)
                .build();

        // when
        lectureHistoryRepository.save(expectedHistory);

        // then
        List<LectureHistory> savedHistory = lectureHistoryRepository.findByLectureId(expectedHistory);

        assertThat(savedHistory).isNotNull();
        assertThat(savedHistory.get(0).getLectureName()).isEqualTo("Test Lecture");
        assertThat(savedHistory.get(0).getLecturerInfo()).isEqualTo("Duckeeee");
        assertThat(savedHistory.get(0).getStatus()).isEqualTo(LectureStatus.SUCCESS);

    }

}
