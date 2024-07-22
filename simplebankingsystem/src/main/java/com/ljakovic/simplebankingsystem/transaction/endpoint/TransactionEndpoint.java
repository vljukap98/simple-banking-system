package com.ljakovic.simplebankingsystem.transaction.endpoint;

import com.ljakovic.simplebankingsystem.transaction.dto.TransactionDto;
import com.ljakovic.simplebankingsystem.transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transaction")
public class TransactionEndpoint {

    private TransactionService transactionService;

    public TransactionEndpoint(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/process")
    public ResponseEntity<TransactionDto> processTransaction(@RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.processTransaction(transactionDto));
    }
}
