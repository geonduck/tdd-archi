package com.tddarchi.domain.lecture.service;

import com.tddarchi.domain.lecture.dto.LectureResult;
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
    public LectureResult applyLecture(Long lectureId, Long userId) {
        Lecture lecture = lectureRepository.findByIdWithPessimisticLock(lectureId);
        if (lecture == null) {
            throw new IllegalStateException(LectureErrorCode.NOT_FOUND_EXCEPTION.getMessage());
        }

        validateEnrollmentPeriod(lecture);
        validateDuplicateApplication(lectureId, userId);

        try {
            validateAndProcessApplication(lecture, userId);
            return LectureResult.success(lecture);
        } catch (IllegalStateException e) {
            recordFailedApplication(lecture, userId);
            throw e;
        }
    }

    private void validateEnrollmentPeriod(Lecture lecture) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(lecture.getEnrollmentStart()) || now.isAfter(lecture.getEnrollmentEnd())) {
            throw new IllegalStateException(LectureErrorCode.NOT_PERIOD_EXCEPTION.getMessage());
        }
    }

    private void validateDuplicateApplication(Long lectureId, Long userId) {
        if (applicationRepository.findByLectureIdAndUserId(lectureId, userId) != null) {
            throw new IllegalStateException(LectureErrorCode.DUPLICATE_APPLICATION_EXCEPTION.getMessage());
        }
    }

    @Transactional
    protected void validateAndProcessApplication(Lecture lecture, Long userId) {
        int currentApplicants = getParticipantCount(lecture.getId());
        if (currentApplicants >= lecture.getMaxParticipants()) {
            throw new IllegalStateException(LectureErrorCode.SOLD_OUT_EXCEPTION.getMessage());
        }

        saveApplication(lecture, userId);
        recordSuccessfulApplication(lecture, userId);
    }

    public int getParticipantCount(Long lectureId) {
        return applicationRepository.countByLectureId(lectureId);
    }

    public List<LectureHistory> findUserLectureHistories(Long userId, LectureStatus success) {
        return historyRepository.findByUserIdAndStatus(userId, success);
    }

    public List<Lecture> findAvailableLectures(LocalDateTime searchDate) {
        return lectureRepository.findLecturesByDate(searchDate);
    }

    private void saveApplication(Lecture lecture, Long userId) {
        applicationRepository.save(LectureApplication.builder()
                .lectureId(lecture.getId())
                .userId(userId)
                .build());
    }

    private void recordSuccessfulApplication(Lecture lecture, Long userId) {
        saveLectureHistory(LectureHistory.builder()
                .lectureId(lecture.getId())
                .userId(userId)
                .status(LectureStatus.SUCCESS)
                .lectureName(lecture.getLectureName())
                .lecturerInfo(lecture.getLecturerInfo())
                .build());
    }

    private void recordFailedApplication(Lecture lecture, Long userId) {
        saveLectureHistory(LectureHistory.builder()
                .lectureId(lecture.getId())
                .userId(userId)
                .status(LectureStatus.FAILED)
                .lectureName(lecture.getLectureName())
                .lecturerInfo(lecture.getLecturerInfo())
                .build());
    }

    @Transactional
    protected void saveLectureHistory(LectureHistory history) {
        historyRepository.save(history);
    }

    @Transactional
    public Lecture saveLecture(Lecture lecture) {
        return lectureRepository.save(lecture);
    }
}