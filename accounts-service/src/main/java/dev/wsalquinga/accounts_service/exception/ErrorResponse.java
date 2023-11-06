package dev.wsalquinga.accounts_service.exception;

import dev.wsalquinga.accounts_service.common.GlobalConstant;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author wsalquinga on 27/10/2023
 */
@Getter
@Setter
public class ErrorResponse {
    private int statusCode;
    private String date;
    private String message;
    private String description;

    public ErrorResponse(int statusCode, LocalDateTime date, String message, String description) {
        this.statusCode = statusCode;
        this.date = date.format(GlobalConstant.DATE_TIME_FORMATTER);
        this.message = message;
        this.description = description;
    }
}
