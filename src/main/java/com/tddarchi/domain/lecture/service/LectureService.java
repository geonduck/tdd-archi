package com.tddarchi.domain.lecture.service;

import com.tddarchi.domain.lecture.entity.Lecture;
import com.tddarchi.domain.lecture.entity.LectureApplication;
import com.tddarchi.domain.lecture.entity.LectureHistory;
import com.tddarchi.domain.lecture.enums.LectureErrorCode;
import com.tddarchi.domain.lecture.enums.LectureStatus;
import com.tddarchi.domain.lecture.repository.ILectureApplicationRepository;
import com.tddarchi.domain.lecture.repository.ILectureHistoryRepository;
import com.tddarchi.domain.lecture.repository.ILectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureService {

    private final ILectureRepository lectureRepository;
    private final ILectureApplicationRepository applicationRepository;
    private final ILectureHistoryRepository historyRepository;

    @Transactional
    public Lecture saveLecture(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    @Transactional
    public Lecture applyLecture(Long lectureId, Long userId) {
        // 1. 강의 존재 확인
        Lecture lecture = lectureRepository.findById(lectureId);
        if (lecture == null) {
            throw new IllegalStateException(LectureErrorCode.NOT_FOUND_EXCEPTION.getMessage());
        }

        // 2. 수강신청 기간 확인
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(lecture.getEnrollmentStart()) || now.isAfter(lecture.getEnrollmentEnd())) {
            throw new IllegalStateException(LectureErrorCode.NOT_PERIOD_EXCEPTION.getMessage());
        }

        // 3. 중복 신청 확인
        LectureApplication existingApplication = applicationRepository.findByLectureIdAndUserId(lectureId, userId);
        if (existingApplication != null) {
            throw new IllegalStateException(LectureErrorCode.DUPLICATE_APPLICATION_EXCEPTION.getMessage());
        }

        // 4. 정원 확인
        int currentApplicants = applicationRepository.countByLectureId(lectureId);
        if (currentApplicants >= lecture.getMaxParticipants()) {
            saveLectureHistory(LectureHistory.builder()
                    .lectureId(lectureId)
                    .userId(userId)
                    .status(LectureStatus.FAILED)
                    .lectureName(lecture.getLectureName())
                    .lecturerInfo(lecture.getLecturerInfo())
                    .build());
            throw new IllegalStateException(LectureErrorCode.SOLD_OUT_EXCEPTION.getMessage());
        }

        // 5. 수강신청 저장
        applicationRepository.save(
                LectureApplication.builder()
                        .lectureId(lectureId)
                        .userId(userId)
                        .appliedAt(LocalDateTime.now())
                        .build()
        );

        // 6. 신청 이력 저장
        saveLectureHistory(LectureHistory.builder()
                .lectureId(lectureId)
                .userId(userId)
                .status(LectureStatus.SUCCESS)
                .lectureName(lecture.getLectureName())
                .lecturerInfo(lecture.getLecturerInfo())
                .build());

        return lecture;
    }

    @Transactional
    public void saveLectureHistory(LectureHistory history) {
        historyRepository.save(history);
    }

    public List<Lecture> findAvailableLectures(LocalDateTime searchDate) {
        return lectureRepository.findLecturesByDate(searchDate);
    }

    public List<LectureHistory> findUserLectureHistories(Long userId, LectureStatus success) {
        return historyRepository.findByUserIdAndStatus(userId, success);
    }

    public Lecture findLectureById(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId);
        if (lecture == null) {
            throw new IllegalStateException(LectureErrorCode.NOT_FOUND_EXCEPTION.getMessage());
        }
        return lecture;
    }

    public int getParticipantCount(Long lectureId) {
        return applicationRepository.countByLectureId(lectureId);
    }

}