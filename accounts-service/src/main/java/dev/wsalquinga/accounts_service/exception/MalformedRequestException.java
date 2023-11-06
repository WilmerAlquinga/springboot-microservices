package dev.wsalquinga.accounts_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * @author wsalquinga on 30/10/2023
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MalformedRequestException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public MalformedRequestException(String message) {
        super(message);
    }
}
