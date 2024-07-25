package com.ljakovic.simplebankingsystem.transaction.endpoint;

import com.ljakovic.simplebankingsystem.service.dataimport.ImportService;
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

    private final TransactionService transactionService;
    private final ImportService importService;

    public TransactionEndpoint(TransactionService transactionService, ImportService importService) {
        this.transactionService = transactionService;
        this.importService = importService;
    }

    @PostMapping("/process")
    public ResponseEntity<TransactionDto> processTransaction(@RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.processTransaction(transactionDto));
    }

    @PostMapping("/import")
    public ResponseEntity<?> importTransactions() {
        importService.importData();
        return ResponseEntity.ok().build();
    }
}
