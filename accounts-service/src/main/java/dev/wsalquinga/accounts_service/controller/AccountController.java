package dev.wsalquinga.accounts_service.controller;

import dev.wsalquinga.accounts_service.common.GlobalConstant;
import dev.wsalquinga.accounts_service.dto.req.AccountReqDTO;
import dev.wsalquinga.accounts_service.dto.res.AccountResDTO;
import dev.wsalquinga.accounts_service.service.AccountService;
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
@RequestMapping(GlobalConstant.API_V1_VERSION + "/accounts")
@Tag(name = "Account Controller", description = "Management Account APIs")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    @Operation(summary = "Get all valid Accounts")
    public ResponseEntity<List<AccountResDTO>> listAll() {
        return ResponseEntity.ok(this.accountService.getAll());
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "Get valid Account by account ID")
    public ResponseEntity<AccountResDTO> findById(@PathVariable(name = "accountId") Long id) {
        return ResponseEntity.ok(this.accountService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new Account")
    public ResponseEntity<AccountResDTO> create(
            @Valid @RequestBody AccountReqDTO accountReqDTO
    ) {
        return new ResponseEntity<>(this.accountService.create(accountReqDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{accountId}")
    @Operation(summary = "Update existing Account by ID")
    public ResponseEntity<AccountResDTO> update(
            @PathVariable(name = "accountId") Long id,
            @Valid @RequestBody AccountReqDTO accountReqDTO
    ) {
        return new ResponseEntity<>(this.accountService.update(accountReqDTO, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{accountId}")
    @Operation(summary = "Delete Account by account ID")
    public ResponseEntity<Void> delete(@PathVariable(name = "accountId") Long id) {
        this.accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
