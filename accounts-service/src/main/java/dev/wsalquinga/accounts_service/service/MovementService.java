package dev.wsalquinga.accounts_service.service;

import dev.wsalquinga.accounts_service.dto.req.MovementReqDTO;
import dev.wsalquinga.accounts_service.dto.res.MovementReportResDTO;
import dev.wsalquinga.accounts_service.dto.res.MovementResDTO;
import dev.wsalquinga.accounts_service.entity.Movement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * @author wsalquinga on 28/10/2023
 */
public interface MovementService {
    Movement getMovementById(Long id);

    MovementResDTO getById(Long id);

    List<MovementResDTO> getAll();

    MovementResDTO create(MovementReqDTO movementReqDTO);

    MovementResDTO update(MovementReqDTO movementReqDTO, Long id);

    void delete(Long id);

    Page<MovementReportResDTO> getAllByClientIdAndBetweenDates(Long clientId, LocalDate from, LocalDate to, Pageable pageable);
}
