package com.tddarchi.domain.lecture.service;

import com.tddarchi.domain.lecture.dto.LectureResult;
import com.tddarchi.domain.lecture.entity.Lecture;
import com.tddarchi.domain.lecture.entity.LectureApplication;
import com.tddarchi.domain.lecture.entity.LectureHistory;
import com.tddarchi.domain.lecture.enums.LectureErrorCode;
import com.tddarchi.domain.lecture.enums.LectureStatus;
import com.tddarchi.domain.lecture.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class LectureServiceTest {

    @Autowired
    private LectureService lectureService;

    private Lecture lecture;
    private final Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        // 테스트용 강의 데이터 생성
        lecture = Lecture.builder()
                .lectureName("Test Lecture")
                .lecturerInfo("Duckeeee")
                .summary("duck is king!!!!!")
                .maxParticipants(30)
                .enrollmentStart(LocalDateTime.now().minusDays(1))
                .enrollmentEnd(LocalDateTime.now().plusDays(5))
                .lectureStart(LocalDateTime.now().plusDays(7))
                .build();

        lecture = lectureService.saveLecture(lecture);
    }

    @Nested
    @DisplayName("수강신청 테스트")
    class ApplyLecture {

        @Test
        @DisplayName("수강신청 성공")
        void applyLecture_ShouldSucceed_WhenValidRequest() {
            // when
            LectureResult result = lectureService.applyLecture(lecture.getId(), USER_ID);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getLectureId()).isEqualTo(lecture.getId());

            // 신청 이력 확인
            List<LectureHistory> histories = lectureService.findUserLectureHistories(USER_ID, LectureStatus.SUCCESS);
            assertThat(histories).hasSize(1);
            assertThat(histories.get(0).getStatus()).isEqualTo(LectureStatus.SUCCESS);
        }

        @Test
        @DisplayName("수강신청 실패 - 정원 초과")
        void applyLecture_ShouldThrowException_WhenSoldOut() {
            // given
            for (int i = 0; i < 30; i++) {
                lectureService.applyLecture(lecture.getId(), 100L + i);
            }

            // when & then
            assertThatThrownBy(() -> lectureService.applyLecture(lecture.getId(), USER_ID))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(String.valueOf(LectureErrorCode.SOLD_OUT_EXCEPTION.getMessage()));
        }

        @Test
        @DisplayName("수강신청 실패 - 중복 신청")
        void applyLecture_ShouldThrowException_WhenDuplicateApplication() {
            // given
            lectureService.applyLecture(lecture.getId(), USER_ID);

            // when & then
            assertThatThrownBy(() -> lectureService.applyLecture(lecture.getId(), USER_ID))
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @Nested
    @DisplayName("강의 조회 테스트")
    class GetLecture {

        @Test
        @DisplayName("날짜별 수강 가능 강의 목록 조회 성공")
        void getLecturesByDate_ShouldReturnAvailableLectures() {
            // when
            List<Lecture> result = lectureService.findAvailableLectures(LocalDateTime.now());

            // then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getLectureName()).isEqualTo("Test Lecture");
        }

        @Test
        @DisplayName("사용자별 신청 완료 강의 목록 조회 성공")
        void getUserLectures_ShouldReturnUserLectures() {
            // given
            lectureService.applyLecture(lecture.getId(), USER_ID);

            // when
            List<LectureHistory> result = lectureService.findUserLectureHistories(USER_ID, LectureStatus.SUCCESS);

            // then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getStatus()).isEqualTo(LectureStatus.SUCCESS);
        }
    }

}
