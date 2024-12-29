package com.tddarchi.interfaces.lecture.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tddarchi.domain.lecture.entity.Lecture;
import com.tddarchi.domain.lecture.service.LectureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LectureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LectureService lectureService;

    @BeforeEach
    void setUp() {
        // 테스트용 강의 데이터 생성
        Lecture lecture = Lecture.builder()
                .lectureName("Test Lecture")
                .lecturerInfo("Duckeeee")
                .summary("duck is king!!!!!")
                .maxParticipants(30)
                .enrollmentStart(LocalDateTime.now().minusDays(1))
                .enrollmentEnd(LocalDateTime.now().plusDays(5))
                .lectureStart(LocalDateTime.now().plusDays(7))
                .build();

        lectureService.saveLecture(lecture);
    }

    @Test
    @DisplayName("강의 신청 성공")
    void applyLecture_ShouldReturnSuccess_WhenLectureIsApplied() throws Exception {
        // given
        Long lectureId = 1L;
        Long userId = 1L;
        String requestBody = objectMapper.writeValueAsString(userId);

        // when & then
        mockMvc.perform(post("/api/v1/lectures/" + lectureId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(lectureId));
    }

}