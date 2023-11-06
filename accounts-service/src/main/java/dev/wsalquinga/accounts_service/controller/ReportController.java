package dev.wsalquinga.accounts_service.controller;

import dev.wsalquinga.accounts_service.common.GlobalConstant;
import dev.wsalquinga.accounts_service.dto.res.MovementReportResDTO;
import dev.wsalquinga.accounts_service.service.MovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @author wsalquinga on 30/10/2023
 */
@RestController
@RequestMapping(GlobalConstant.API_V1_VERSION + "/reports")
@Tag(name = "Report Controller", description = "Management Report APIs")
public class ReportController {
    private final MovementService movementService;

    public ReportController(MovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping
    @Operation(summary = "Get valid Movement by movement ID", tags = {"movements", "get"})
    public ResponseEntity<Page<MovementReportResDTO>> findById(
            @RequestParam(name = "clienteId") Long clientId,
            @RequestParam(name = "fechaDesde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "fechaHasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.movementService.getAllByClientIdAndBetweenDates(clientId, from, to, pageable));
    }
}
