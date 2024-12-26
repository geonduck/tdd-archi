package com.tddarchi.interfaces.lecture.controller;

import com.tddarchi.domain.lecture.dto.LectureResult;
import com.tddarchi.domain.lecture.entity.Lecture;
import com.tddarchi.domain.lecture.entity.LectureHistory;
import com.tddarchi.domain.lecture.enums.LectureStatus;
import com.tddarchi.domain.lecture.service.LectureService;
import com.tddarchi.interfaces.lecture.dto.LectureHistoryResponse;
import com.tddarchi.interfaces.lecture.dto.LectureResponse;
import com.tddarchi.support.http.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lectures")
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;

    @PostMapping("/{lectureId}")
    public ResponseEntity<ApiResponse<LectureResponse>> applyLecture(
            @PathVariable(name = "lectureId") Long lectureId,
            @RequestBody Long userId) {
        LectureResult result = lectureService.applyLecture(lectureId, userId);
        return ResponseEntity.ok(ApiResponse.success(LectureResponse.from(result)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LectureResponse>>> getAvailableLectures(
            @RequestParam(name = "searchDate") @DateTimeFormat(pattern = "yyyyMMdd") LocalDateTime searchDate) {
        List<Lecture> lectures = lectureService.findAvailableLectures(searchDate);
        return ResponseEntity.ok(ApiResponse.success(
                lectures.stream().map(LectureResponse::from).collect(Collectors.toList())
        ));
    }

    @GetMapping("/users/me")
    public ResponseEntity<ApiResponse<List<LectureHistoryResponse>>> getUserLectures(
            @RequestBody Long userId) {
        List<LectureHistory> histories = lectureService.findUserLectureHistories(userId, LectureStatus.SUCCESS);
        return ResponseEntity.ok(ApiResponse.success(
                histories.stream().map(LectureHistoryResponse::from).collect(Collectors.toList())
        ));
    }
}