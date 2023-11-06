package dev.wsalquinga.accounts_service.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author wsalquinga on 28/10/2023
 */
@Getter
@Setter
@Builder
public class MovementResDTO {
    private Long id;
    private LocalDateTime date;
    private String movementType;
    private BigDecimal amount;
    private BigDecimal balance;
    private AccountResDTO account;
}
