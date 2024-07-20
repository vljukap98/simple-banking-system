package com.ljakovic.simplebankingsystem.account.endpoint;

import com.ljakovic.simplebankingsystem.account.dto.AccountDto;
import com.ljakovic.simplebankingsystem.account.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/account")
public class AccountEndpoint {

    private final AccountService accountService;

    public AccountEndpoint(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/new")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountService.createAccount(accountDto));
    }
}
