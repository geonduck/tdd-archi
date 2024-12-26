package com.tddarchi.infra.lecture.JpaRepository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tddarchi.domain.lecture.entity.Lecture;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Lecture l WHERE l.id = :id")
    Lecture findByIdWithPessimisticLock(@Param("id") Long id);

    @Query("SELECT l FROM Lecture l " +
            "WHERE l.enrollmentStart <= :searchDate " +
            "AND l.enrollmentEnd >= :searchDate " +
            "ORDER BY l.lectureStart ASC")
    List<Lecture> findLecturesByDate(@Param("searchDate") LocalDateTime searchDate);
}
