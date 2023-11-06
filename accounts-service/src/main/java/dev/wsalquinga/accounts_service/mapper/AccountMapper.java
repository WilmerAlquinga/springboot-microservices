package dev.wsalquinga.accounts_service.mapper;

import dev.wsalquinga.accounts_service.dto.req.AccountReqDTO;
import dev.wsalquinga.accounts_service.dto.res.AccountResDTO;
import dev.wsalquinga.accounts_service.entity.Account;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author wsalquinga on 28/10/2023
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResDTO toAccountResDTO(Account account);

    List<AccountResDTO> toAccountResDTO(List<Account> account);

    Account toAccountEntity(AccountReqDTO accountReqDTO);
}
