package dev.wsalquinga.accounts_service.controller;

import dev.wsalquinga.accounts_service.common.GlobalConstant;
import dev.wsalquinga.accounts_service.dto.req.MovementReqDTO;
import dev.wsalquinga.accounts_service.dto.res.MovementResDTO;
import dev.wsalquinga.accounts_service.service.MovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wsalquinga on 28/10/2023
 */
@RestController
@RequestMapping(GlobalConstant.API_V1_VERSION + "/movements")
@Tag(name = "Movement Controller", description = "Management Movement APIs")
public class MovementController {
    private final MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping
    @Operation(summary = "Get all valid Movements")
    public ResponseEntity<List<MovementResDTO>> listAll() {
        return ResponseEntity.ok(this.movementService.getAll());
    }

    @GetMapping("/{movementId}")
    @Operation(summary = "Get valid Movement by movement ID")
    public ResponseEntity<MovementResDTO> findById(@PathVariable(name = "movementId") Long id) {
        return ResponseEntity.ok(this.movementService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new Movement")
    public ResponseEntity<MovementResDTO> create(
            @Valid @RequestBody MovementReqDTO movementReqDTO
    ) {
        return new ResponseEntity<>(this.movementService.create(movementReqDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{movementId}")
    @Operation(summary = "Update existing Movement by ID")
    public ResponseEntity<MovementResDTO> update(
            @PathVariable(name = "movementId") Long id,
            @Valid @RequestBody MovementReqDTO movementReqDTO
    ) {
        return new ResponseEntity<>(this.movementService.update(movementReqDTO, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{movementId}")
    @Operation(summary = "Delete Movement by movement ID")
    public ResponseEntity<Void> delete(@PathVariable(name = "movementId") Long id) {
        this.movementService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
