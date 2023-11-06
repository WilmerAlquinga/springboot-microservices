package dev.wsalquinga.accounts_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * @author wsalquinga on 29/10/2023
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntityConstraintException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EntityConstraintException(String message) {
        super(message);
    }
}
