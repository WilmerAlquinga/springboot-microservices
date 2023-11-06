package dev.wsalquinga.accounts_service.mapper;

import dev.wsalquinga.accounts_service.dto.req.MovementReqDTO;
import dev.wsalquinga.accounts_service.dto.res.MovementReportResDTO;
import dev.wsalquinga.accounts_service.dto.res.MovementResDTO;
import dev.wsalquinga.accounts_service.entity.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author wsalquinga on 28/10/2023
 */
@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface MovementMapper {
    MovementResDTO toMovementResDTO(Movement movement);

    List<MovementResDTO> toMovementResDTO(List<Movement> movement);

    Movement toMovementEntity(MovementReqDTO movementReqDTO);

    @Mapping(target = "accountNumber", source = "account.accountNumber")
    @Mapping(target = "accountType", source = "account.accountType")
    @Mapping(target = "accountStatus", source = "account.status")
    @Mapping(target = "availableBalance", source = "balance")
    MovementReportResDTO toMovementReportResDTO(Movement movement);
}
