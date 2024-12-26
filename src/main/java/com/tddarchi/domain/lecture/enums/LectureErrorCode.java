package com.tddarchi.domain.lecture.enums;

import com.tddarchi.support.exception.ErrorCode;
import lombok.*;

@Getter
@AllArgsConstructor
public enum LectureErrorCode implements ErrorCode {
    SOLD_OUT_EXCEPTION("수강 인원이 마감되었습니다."),
    DUPLICATE_APPLICATION_EXCEPTION("중복 신청입니다."),
    NOT_FOUND_EXCEPTION("요청정보를 확인하세요."),
    ;
    private final String message;

}
