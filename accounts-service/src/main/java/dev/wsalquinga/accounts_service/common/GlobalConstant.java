package dev.wsalquinga.accounts_service.common;

import java.time.format.DateTimeFormatter;

/**
 * @author wsalquinga on 27/10/2023
 */
public class GlobalConstant {
    public static final String API_V1_VERSION = "/api/v1";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static final String CLIENTS_SERVICE_URI = "/clients";
}
