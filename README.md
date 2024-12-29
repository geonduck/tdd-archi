
### **프로젝트 디렉토리 구조 v26**

```
 /support/
    ├── http/
    │   ├── CommonResponse.java       # 표준 응답 처리
    ├── exception/
    │   ├── CommonException.java        # 공통 예외 클래스
    │   ├── ErrorCode.java              # 공통 예외 코드
    ├── entity/
    │   ├── BaseTimeEntity.java         # 공통 Entity 클래스

 /interfaces/
    ├── lecture/
    │   ├── controller/
    │   │   ├── LectureController.java    # API 엔드포인트
    │   ├── dto/
    │       ├── request/                  # 요청 DTO
    │       │   ├── LectureRequest.java
    │       │   ├── LectureSearchRequest.java
    │       ├── response/                 # 응답 DTO
    │           ├── LectureResponse.java
    │           ├── LectureListResponse.java
 /domain/    
    ├── lecture/
    │   ├── entity/
    │   │   ├── Lecture.java              # 강의 (Entity)
    │   │   ├── LectureApplication.java   # 수강 신청 (Entity)
    │   │   ├── LectureHistory.java       # 강의 기록 (Entity)
    │   ├── repository/
    │   │   ├── ILectureRepository.java                   # Repository 인터페이스
    │   │   ├── ILectureApplicationRepository.java        # Repository 인터페이스
    │   │   ├── ILectureHistoryRepository.java            # Repository 인터페이스
    │   ├── enums/
    │   │   ├── LectureStatus.java                          # Enum (강의 상태)
    │   │   ├── LectureErrorCode.java                       # 강의 예외 클래스
    │   ├── service/                          
    │   │   ├── LectureService.java                       # Service
    │   ├── dto/
    │   │   ├── LectureCommand.java       # Command DTO (사용자 입력)
    │   │   ├── LectureInfo.java          # 정보 DTO (출력)
    │   ├── validation/
    │       ├── LectureValidation.java    # Validation 클래스
/infra/    
    ├── lecture/
        ├── repository/
        │   ├── LectureRepositoryImpl.java                 # Repository 구현체
        │   ├── LectureApplicationRepositoryImpl.java      # Repository 구현체
        │   ├── LectureHistoryRepositoryImpl.java          # Repository 구현체
        ├── JpaRepository/
        │   ├── LectureJpaRepository.java                 # JpaRepository
        │   ├── LectureApplicationJpaRepository.java      # JpaRepository
        │   ├── LectureHistoryJpaRepository.java          # JpaRepository
```


### 1. **특강 신청 API (POST /api/v1/lectures/{lecture_id})**

### 기본 테스트 케이스:

- **정상 신청**: 유효한 `lecture_id`와 `user_id`로 신청하면 성공해야 한다.
- **30명 초과 시 신청 실패**: 30명이 초과된 경우, Exception이 발생해야 한다.
- **중복 신청 방지**: 동일한 사용자가 동일한 강의에 중복 신청 시 Exception이 발생해야 한다.
- **없는 강의 신청**: 존재하지 않는 `lecture_id`로 신청할 경우 Exception이 발생해야 한다.

### 동시성 테스트:

- **동시 신청 테스트 (경쟁상황)**: 여러 사용자가 동시에 강의 신청을 시도할 때 정확하게 30명만 신청에 성공하는지 검증.
- **동일 사용자 중복 신청 방지**: 동일한 사용자가 동시에 여러 요청을 보낼 때 중복 신청이 발생하지 않도록 보장.

### 2. **특강 신청 가능 목록 조회 API (GET /api/v1/lectures?searchDate=?)**

### 기본 테스트 케이스:

- **신청 가능 강의 조회**: 해당 날짜에 신청 가능한 강의 목록이 정확히 반환되어야 한다.
- **정원이 찬 강의 필터링**: 정원이 다 찬 강의는 목록에서 제외되어야 한다.
- **존재하지 않는 날짜 범위**: 존재하지 않는 날짜에 대한 요청 시 빈 목록을 반환해야 한다.

### 3. **특강 신청 완료 목록 조회 API (GET /api/v1/users/me)**

### 기본 테스트 케이스:

- **신청 완료 목록 조회**: 특정 사용자의 신청 완료 강의 목록을 정확히 반환해야 한다.
- **신청 기록이 없는 경우**: 신청 기록이 없는 경우 빈 목록을 반환해야 한다.

1. **신청 실패 시 이력 기록 테스트**:
    - 수강 신청 실패 시, 실패 사유 및 강의 정보를 `LectureHistory`에 제대로 기록하는지 확인.
2. **신청 성공 시 이력 기록 테스트**:
    - 수강 신청 성공 시, 해당 내역이 정상적으로 `LectureHistory`에 기록되는지 테스트.
3. **강의 수강 인원 마감 후 추가 신청 제한 테스트**:
4. **성공 및 실패 이력 확인 테스트**:
    - 특정 사용자의 신청 내역이 `LectureHistory`에 정확히 반영되었는지 확인.
    - 성공/실패 여부와 함께 기록된 강의 정보(lectureName, lecturerInfo 등)가 맞는지 확인.
5. **수강 신청 시작 및 마감일 확인 테스트**:
    - 수강 신청이 시작되기 전, 또는 마감된 후에는 신청이 불가능한지 테스트.
