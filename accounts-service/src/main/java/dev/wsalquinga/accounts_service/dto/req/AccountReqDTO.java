package dev.wsalquinga.accounts_service.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author wsalquinga on 28/10/2023
 */
@Getter
@Setter
@Builder
public class AccountReqDTO {
    private Long id;

    @NotNull(message = "Id de Cliente es requerido")
    private Long clientId;

    @NotBlank(message = "Número de Cuenta es requerido")
    private String accountNumber;

    @NotBlank(message = "Tipo de Cuenta es requerido")
    private String accountType;

    @NotNull(message = "Balance inicial es requerido")
    private BigDecimal balance;

    @NotNull(message = "Estado es requerido")
    private Boolean status;
}
