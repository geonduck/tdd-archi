package com.tddarchi.domain.lecture.service;

import com.tddarchi.domain.lecture.entity.Lecture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class LectureConcurrentIntegrationTest {

    @Autowired
    private LectureService lectureService;

    @Test
    @DisplayName("40명 동시 신청 시 정확히 30명만 성공")
    void should_accept_only_30_participants_when_40_concurrent_requests() {
        int maxParticipants = 30;
        int concurrentUsers = 40;
        Lecture lecture = createTestLecture(maxParticipants);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        List<CompletableFuture<Void>> futures = IntStream.range(0, concurrentUsers)
                .mapToObj(i -> CompletableFuture.runAsync(() -> {
                    try {
                        lectureService.applyLecture(lecture.getId(), (long) (i + 1));
                        successCount.incrementAndGet();
                    } catch (Exception e) {
                        failCount.incrementAndGet();
                    }
                }))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .join();

        assertAll(
                () -> assertThat(successCount.get()).isEqualTo(maxParticipants),
                () -> assertThat(failCount.get()).isEqualTo(concurrentUsers - maxParticipants),
                () -> assertThat(lectureService.getParticipantCount(lecture.getId())).isEqualTo(maxParticipants)
        );
    }

    @Test
    @DisplayName("데이터 일관성 검증")
    void should_maintain_data_consistency_under_concurrent_requests() {
        int maxParticipants = 30;
        int concurrentUsers = 40;
        Lecture lecture = createTestLecture(maxParticipants);

        List<CompletableFuture<Void>> futures = IntStream.range(0, concurrentUsers)
                .mapToObj(i -> CompletableFuture.runAsync(() -> {
                    try {
                        lectureService.applyLecture(lecture.getId(), (long) (i + 1));
                    } catch (Exception ignored) {
                    }
                }))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .join();

        assertThat(lectureService.getParticipantCount(lecture.getId())).isEqualTo(maxParticipants);
    }

    private Lecture createTestLecture(int maxParticipants) {
        return lectureService.saveLecture(Lecture.builder()
                .lectureName("Test Lecture")
                .lecturerInfo("Test Lecturer")
                .maxParticipants(maxParticipants)
                .enrollmentStart(LocalDateTime.now().minusDays(1))
                .enrollmentEnd(LocalDateTime.now().plusDays(1))
                .lectureStart(LocalDateTime.now().plusDays(7))
                .build());
    }
}