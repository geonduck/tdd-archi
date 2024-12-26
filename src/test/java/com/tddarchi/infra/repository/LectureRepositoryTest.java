package com.tddarchi.infra.repository;

import com.tddarchi.domain.lecture.entity.Lecture;
import com.tddarchi.infra.lecture.repository.LectureRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class LectureRepositoryTest {

    @Autowired
    private LectureRepositoryImpl lectureRepository;

    private Lecture lecture;

    @BeforeEach
    void setUp() {
        lecture = Lecture.builder()
                .lectureName("Test Lecture")
                .lecturerInfo("Duckeeee")
                .summary("duck is king!!!!!")
                .maxParticipants(30)
                .enrollmentStart(LocalDateTime.of(2024, 12, 15, 15, 30))
                .enrollmentEnd(LocalDateTime.of(2024, 12, 30, 15, 30))
                .lectureStart(LocalDateTime.of(2024, 12, 25, 15, 30))
                .build();

    }


    @Test
    @DisplayName("강의 ID로 조회 성공")
    void findById_ShouldReturnLecture_WhenLectureExists() {
        // given
        lectureRepository.save(lecture);
        // when
        Lecture foundLecture = lectureRepository.findById(lecture.getId());
        // then
        assertThat(foundLecture.getLectureName()).isEqualTo("Test Lecture");
        assertThat(foundLecture.getEnrollmentEnd()).isEqualTo(LocalDateTime.of(2024, 12, 30, 15, 30));
        assertThat(foundLecture.getLectureStart()).isEqualTo(LocalDateTime.of(2024, 12, 25, 15, 30));
    }

}