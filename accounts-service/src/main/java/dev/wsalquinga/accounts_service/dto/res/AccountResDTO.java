package dev.wsalquinga.accounts_service.dto.res;

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
public class AccountResDTO {
    private Long id;
    private Long clientId;
    private String clientName;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private Boolean status;
}
