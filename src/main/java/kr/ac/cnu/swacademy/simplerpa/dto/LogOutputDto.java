package kr.ac.cnu.swacademy.simplerpa.dto;

import lombok.Builder;

public class LogOutputDto {
    private LogStatus logStatus;
    private String message;

    @Builder
    public LogOutputDto(LogStatus logStatus, String message) {
        this.logStatus = logStatus;
        this.message = message;
    }
}
