package dev.wsalquinga.clients_service.controller;

import dev.wsalquinga.clients_service.common.GlobalConstant;
import dev.wsalquinga.clients_service.dto.ClientDTO;
import dev.wsalquinga.clients_service.dto.projection.ClientProjectionDTO;
import dev.wsalquinga.clients_service.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wsalquinga on 27/10/2023
 */
@RestController
@RequestMapping(GlobalConstant.API_V1_VERSION + "/clients")
@Tag(name = "Client Controller", description = "Management Clients APIs")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @Operation(summary = "Get all valid Clients", tags = {"clients", "get"})
    public ResponseEntity<List<ClientDTO>> listAll() {
        return ResponseEntity.ok(this.clientService.getAll());
    }

    @GetMapping("/{clientId}")
    @Operation(summary = "Get valid Client by client ID", tags = {"clients", "get"})
    public ResponseEntity<ClientDTO> findById(@PathVariable(name = "clientId") Long id) {
        return ResponseEntity.ok(this.clientService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new Client", tags = {"clients", "post"})
    public ResponseEntity<ClientDTO> create(
            @Valid @RequestBody ClientDTO client
    ) {
        return new ResponseEntity<>(this.clientService.create(client), HttpStatus.CREATED);
    }

    @PutMapping("/{clientId}")
    @Operation(summary = "Update existing Client by ID", tags = {"clients", "put"})
    public ResponseEntity<ClientDTO> update(
            @PathVariable(name = "clientId") Long id,
            @Valid @RequestBody ClientDTO clientDTO
    ) {
        return new ResponseEntity<>(this.clientService.update(clientDTO, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{clientId}")
    @Operation(summary = "Delete Client by client ID", tags = {"clients", "delete"})
    public ResponseEntity<Void> delete(@PathVariable(name = "clientId") Long id) {
        this.clientService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get-name/{clientId}")
    @Operation(summary = "Get valid Name of Client by client ID", tags = {"clients", "get"})
    public ResponseEntity<ClientProjectionDTO> findNameById(@PathVariable(name = "clientId") Long id) {
        return ResponseEntity.ok(this.clientService.getNameById(id));
    }

    @GetMapping("/get-names/{clientIds}")
    @Operation(summary = "Get valid Names of Clients by client IDs", tags = {"clients", "get"})
    public ResponseEntity<List<ClientProjectionDTO>> findNamesByIds(@PathVariable(name = "clientIds") List<Long> ids) {
        return ResponseEntity.ok(this.clientService.getNamesByIds(ids));
    }
}
