package com.vuestagram.backend.config;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ApiStatus {

    SUCCESS(HttpStatus.OK, "요청이 성공적으로 처리되었습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    ApiStatus(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}

