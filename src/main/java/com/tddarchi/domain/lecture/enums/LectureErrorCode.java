package com.tddarchi.domain.lecture.enums;

import com.tddarchi.support.exception.ErrorCode;
import lombok.*;

@Getter
@AllArgsConstructor
public enum LectureErrorCode implements ErrorCode {
    SOLD_OUT_EXCEPTION("수강 인원이 마감되었습니다."),
    DUPLICATE_APPLICATION_EXCEPTION("이미 신청한 강의입니다."),
    NOT_FOUND_EXCEPTION("요청정보를 확인하세요."),
    NOT_PERIOD_EXCEPTION("날짜를 확인하세요."),
    ;
    private final String message;

}
