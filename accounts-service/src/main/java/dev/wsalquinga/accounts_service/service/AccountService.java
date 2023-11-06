package dev.wsalquinga.accounts_service.service;

import dev.wsalquinga.accounts_service.dto.ClientDTO;
import dev.wsalquinga.accounts_service.dto.req.AccountReqDTO;
import dev.wsalquinga.accounts_service.dto.res.AccountResDTO;
import dev.wsalquinga.accounts_service.entity.Account;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wsalquinga on 28/10/2023
 */
public interface AccountService {
    Account getAccountById(Long id);

    AccountResDTO getById(Long id);

    List<AccountResDTO> getAll();

    AccountResDTO create(AccountReqDTO accountReqDTO);

    AccountResDTO update(AccountReqDTO accountReqDTO, Long id);

    void delete(Long id);

    void updateBalance(Account account, BigDecimal newBalance);

    ClientDTO getClientByIdFromClientsService(Long clientId);

    List<ClientDTO> getClientsByIdsFromClientService(String ids);
}
