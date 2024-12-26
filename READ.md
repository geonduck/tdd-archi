# ERD

### Reference Documentation
```
+----------------+       +---------------------+
|    Lecture     |       |  LectureApplication |
+----------------+       +---------------------+
| id (PK)        |<----->| id (PK)             |
| lectureName    |       | lecture_id (FK)     |
| lecturerInfo   |       | user_id (FK)        |
| maxParticipants|       | createdDate         |
| summary        |       | modifiedDate        |
| enrollmentStart|       +---------------------+
| enrollmentEnd  |
| lectureStart   |
| createdDate    |
| modifiedDate   |
+----------------+

+---------------------+
|   LectureHistory    |
+---------------------+
| id (PK)             |
| lecture_id (FK)     |
| user_id (FK)        |
| createdDate         |
| modifiedDate        |
| status              |
| lectureName         |
| lecturerInfo        |
+---------------------+

```

- 특강 테이블 (Lecture)
- 특강 신청 테이블 (LectureApplication)
- 특강 신청 이력 테이블 (LectureHistory)
- 특강신청 성공되는 데이터만 신청 테이블에 저장
- 특강 ID 기준으로 신청인원 확인
- 특강 + 사용자 ID 로 중복 여부 확인
- 신청하는 특강관련 정보는 이력 테이블에 저장
- 이력 테이블에는 실패이력까지 저장되며 강의 정보를 함께 저장