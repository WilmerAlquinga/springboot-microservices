package dev.wsalquinga.accounts_service.service.impl;

import dev.wsalquinga.accounts_service.dto.ClientDTO;
import dev.wsalquinga.accounts_service.dto.req.MovementReqDTO;
import dev.wsalquinga.accounts_service.dto.res.MovementReportResDTO;
import dev.wsalquinga.accounts_service.dto.res.MovementResDTO;
import dev.wsalquinga.accounts_service.entity.Account;
import dev.wsalquinga.accounts_service.entity.Movement;
import dev.wsalquinga.accounts_service.exception.MalformedRequestException;
import dev.wsalquinga.accounts_service.exception.ResourceNotFoundException;
import dev.wsalquinga.accounts_service.mapper.MovementMapper;
import dev.wsalquinga.accounts_service.repository.MovementRepository;
import dev.wsalquinga.accounts_service.service.AccountService;
import dev.wsalquinga.accounts_service.service.MovementService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wsalquinga on 28/10/2023
 */
@Service
@Slf4j
public class MovementServiceImpl implements MovementService {
    private final MovementRepository movementRepository;
    private final MovementMapper movementMapper;
    private final AccountService accountService;

    public MovementServiceImpl(MovementRepository movementRepository, MovementMapper movementMapper, AccountService accountService) {
        this.movementRepository = movementRepository;
        this.movementMapper = movementMapper;
        this.accountService = accountService;
    }

    @Override
    public Movement getMovementById(Long id) {
        log.info("Find movement by id: {}", id);
        Movement movement = this.movementRepository.findValidById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró El Movimiento con el id: " + id)
        );
        log.info("Movement retrieved: {}", movement);
        return movement;
    }

    @Override
    public MovementResDTO getById(Long id) {
        return this.movementMapper.toMovementResDTO(this.getMovementById(id));
    }

    @Override
    public List<MovementResDTO> getAll() {
        List<MovementResDTO> movements = this.movementMapper.toMovementResDTO(this.movementRepository.findAllValid());
        for (MovementResDTO movement : movements) {
            movement.setAccount(this.accountService.getById(movement.getAccount().getId()));
        }
        return movements;
    }

    @Override
    @Transactional
    public MovementResDTO create(MovementReqDTO movementReqDTO) {
        Movement movement = this.movementMapper.toMovementEntity(movementReqDTO);
        Account account = this.accountService.getAccountById(movementReqDTO.getAccountId());
        BigDecimal totalBalance = account.getBalance().add(movement.getAmount());
        this.accountService.updateBalance(account, totalBalance);
        movement.setAccount(account);
        movement.setBalance(totalBalance);
        movement = this.movementRepository.save(movement);
        log.info("Movement created: {}", movement);
        return this.movementMapper.toMovementResDTO(movement);
    }

    @Override
    @Transactional
    public MovementResDTO update(MovementReqDTO movementReqDTO, Long id) {
        Movement movement = this.getMovementById(id);
        Account account = movement.getAccount();
        BigDecimal newBalance = account.getBalance().subtract(movement.getAmount()).add(movementReqDTO.getAmount());
        this.accountService.updateBalance(account, newBalance);
        movement.setAmount(movementReqDTO.getAmount());
        movement.setBalance(newBalance);
        movement.setDate(movementReqDTO.getDate());
        movement.setAccount(account);
        movement = this.movementRepository.save(movement);
        log.info("Movement created: {}", movement);
        return this.movementMapper.toMovementResDTO(movement);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Movement movement = this.getMovementById(id);
        Account account = movement.getAccount();
        BigDecimal newBalance = account.getBalance().subtract(movement.getAmount());
        this.accountService.updateBalance(account, newBalance);
        movement.setDeletedAt(LocalDateTime.now());
        this.movementRepository.save(movement);
        log.info("Movement with id: {} successfully deleted", id);
    }

    @Override
    public Page<MovementReportResDTO> getAllByClientIdAndBetweenDates(Long clientId, LocalDate from, LocalDate to, Pageable pageable) {
        LocalDateTime dateFrom = from.atStartOfDay();
        LocalDateTime dateTo = to.atTime(23, 59, 59);
        if (dateTo.isBefore(dateFrom))
            throw new MalformedRequestException("La fecha desde no puede ser mayor a la fecha hasta");
        Page<MovementReportResDTO> movements = this.movementRepository.findByClientIdAndBetweenDates(clientId, dateFrom, dateTo, pageable)
                .map(this.movementMapper::toMovementReportResDTO);
        if (movements.isEmpty()) return movements;
        ClientDTO client = this.accountService.getClientByIdFromClientsService(clientId);
        for (MovementReportResDTO movement : movements) {
            movement.setName(client.getName());
            movement.setInitialBalance(movement.getAvailableBalance().subtract(movement.getAmount()));
        }
        return movements;
    }
}
