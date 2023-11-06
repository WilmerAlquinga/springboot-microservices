package dev.wsalquinga.accounts_service.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author wsalquinga on 30/10/2023
 */
@Data
@Builder
public class MovementReportResDTO {
    @JsonProperty("Fecha")
    private LocalDateTime date;

    @JsonProperty("Cliente")
    private String name;

    @JsonProperty("Número de Cuenta")
    private String accountNumber;

    @JsonProperty("Tipo")
    private String accountType;

    @JsonProperty("Saldo inicial")
    private BigDecimal initialBalance;

    @JsonProperty("Estado")
    private Boolean accountStatus;

    @JsonProperty("Movimiento")
    private BigDecimal amount;

    @JsonProperty("Saldo Disponible")
    private BigDecimal availableBalance;
}
