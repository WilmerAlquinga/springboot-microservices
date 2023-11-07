package dev.wsalquinga.clients_service.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wsalquinga on 6/11/2023
 */
@Getter
@AllArgsConstructor
public enum ExceptionMessageEnum {
    CLIENT_NOT_FOUND("No se entonctró el cliente con id: ");

    private final String message;
}
