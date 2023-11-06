package dev.wsalquinga.accounts_service.service.impl;

import dev.wsalquinga.accounts_service.common.GlobalConstant;
import dev.wsalquinga.accounts_service.config.WebClientConfig;
import dev.wsalquinga.accounts_service.dto.ClientDTO;
import dev.wsalquinga.accounts_service.dto.req.AccountReqDTO;
import dev.wsalquinga.accounts_service.dto.res.AccountResDTO;
import dev.wsalquinga.accounts_service.entity.Account;
import dev.wsalquinga.accounts_service.exception.EntityConstraintException;
import dev.wsalquinga.accounts_service.exception.ResourceNotFoundException;
import dev.wsalquinga.accounts_service.exception.ServerErrorException;
import dev.wsalquinga.accounts_service.mapper.AccountMapper;
import dev.wsalquinga.accounts_service.repository.AccountRepository;
import dev.wsalquinga.accounts_service.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wsalquinga on 28/10/2023
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final WebClientConfig webClient;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper, WebClientConfig webClient) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.webClient = webClient;
    }

    @Override
    public Account getAccountById(Long id) {
        log.info("Find account by id: {}", id);
        Account account = this.accountRepository.findValidById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se encontró la Cuenta con el id: " + id));
        log.info("Account retrieved: {}", account);
        return account;
    }

    @Override
    public AccountResDTO getById(Long id) {
        AccountResDTO account = this.accountMapper.toAccountResDTO(this.getAccountById(id));
        ClientDTO client = this.getClientByIdFromClientsService(account.getClientId());
        assert client != null;
        account.setClientName(client.getName());
        return account;
    }

    @Override
    public List<AccountResDTO> getAll() {
        log.info("Find all accounts");
        List<Account> accounts = this.accountRepository.findAllValid();
        if (accounts.isEmpty()) return new ArrayList<>();
        log.info("Accounts: {}", accounts.size());
        String clientIds = accounts.stream().map(account -> account.getClientId() + "")
                .collect(Collectors.joining(","));

        List<ClientDTO> clients = this.getClientsByIdsFromClientService(clientIds);
        // Return only accounts with valid Clients
        List<AccountResDTO> validAccounts = new ArrayList<>();
        if (clients == null || clients.isEmpty()) return validAccounts;
        Map<Long, ClientDTO> mapClients = new HashMap<>();
        for (ClientDTO client : clients) {
            mapClients.put(client.getId(), client);
        }
        for (Account account : accounts) {
            ClientDTO client = mapClients.get(account.getClientId());
            if (client != null) {
                AccountResDTO accountResDTO = this.accountMapper.toAccountResDTO(account);
                accountResDTO.setClientName(client.getName());
                validAccounts.add(accountResDTO);
            }
        }
        log.info("Accounts with valid Clients: {}", validAccounts.size());
        return validAccounts;
    }

    @Override
    @Transactional
    public AccountResDTO create(AccountReqDTO accountReqDTO) {
        log.info("Create account: {}", accountReqDTO.getAccountNumber());
        this.verifyAccountNumber(accountReqDTO.getAccountNumber());
        this.verifyInsufficientBalance(accountReqDTO.getBalance());
        Account account = this.accountMapper.toAccountEntity(accountReqDTO);

        ClientDTO client = this.getClientByIdFromClientsService(account.getClientId());
        account = this.accountRepository.save(account);
        log.info("Account created: {}", account);
        return this.accountMapper.toAccountResDTO(account);
    }

    @Override
    @Transactional
    public AccountResDTO update(AccountReqDTO accountReqDTO, Long id) {
        Account account = this.getAccountById(id);
        if (!account.getAccountNumber().equals(accountReqDTO.getAccountNumber()))
            this.verifyAccountNumber(accountReqDTO.getAccountNumber());
        this.verifyInsufficientBalance(accountReqDTO.getBalance());
        account.setAccountNumber(accountReqDTO.getAccountNumber());
        account.setAccountType(accountReqDTO.getAccountType());
        account.setStatus(accountReqDTO.getStatus());
        account.setBalance(accountReqDTO.getBalance());
        account = this.accountRepository.save(account);
        log.info("Account updated: {}", account);
        return this.accountMapper.toAccountResDTO(account);
    }

    @Override
    public void delete(Long id) {
        Account account = this.getAccountById(id);
        account.setDeletedAt(LocalDateTime.now());
        this.accountRepository.save(account);
        log.info("Account with id: {} successfully deleted", id);
    }

    @Override
    public void updateBalance(Account account, BigDecimal newBalance) {
        this.verifyInsufficientBalance(newBalance);
        account.setBalance(newBalance);
        this.accountRepository.save(account);
        log.info("Account balance updated");
    }

    @Override
    public ClientDTO getClientByIdFromClientsService(Long clientId) {
        return this.webClient.webClient()
                .get()
                .uri(GlobalConstant.CLIENTS_SERVICE_URI + "/get-name/" + clientId)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    throw new ServerErrorException("Error al recuperar los datos del Cliente");
                })
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    throw new ResourceNotFoundException("No se encontró el Cliente con el id: " + clientId);
                })
                .bodyToMono(ClientDTO.class)
                .block();
    }

    @Override
    public List<ClientDTO> getClientsByIdsFromClientService(String ids) {
        ParameterizedTypeReference<List<ClientDTO>> typeReference = new ParameterizedTypeReference<>() {
        };

        return this.webClient.webClient()
                .get()
                .uri(GlobalConstant.CLIENTS_SERVICE_URI + "/get-names/" + ids)
                .retrieve()
                .bodyToMono(typeReference)
                .block();
    }

    private void verifyAccountNumber(String accountNumber) {
        if (this.accountRepository.existsByAccountNumber(accountNumber))
            throw new EntityConstraintException("Ya existe una Cuenta con el número: " + accountNumber);
    }

    private void verifyInsufficientBalance(BigDecimal balance) {
        if (balance.compareTo(BigDecimal.ZERO) < 0) throw new EntityConstraintException("Saldo no disponible");
    }
}
