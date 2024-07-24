package com.ljakovic.simplebankingsystem.service.dataimport;

import com.ljakovic.simplebankingsystem.account.model.Account;
import com.ljakovic.simplebankingsystem.account.model.ECurrency;
import com.ljakovic.simplebankingsystem.account.service.AccountService;
import com.ljakovic.simplebankingsystem.service.dataimport.dto.ImportDto;
import com.ljakovic.simplebankingsystem.transaction.service.TransactionService;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ImportService {

    private static final Logger LOG = LoggerFactory.getLogger(ImportService.class);

    private static final int THREAD_POOL_SIZE = 10;
    private static final int BATCH_SIZE = 1000;

    private final TransactionService transactionService;
    private final AccountService accountService;

    public ImportService(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @PostConstruct
    public void importData() {
        String csvFilePath = System.getProperty("import.csv");
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\PC\\Desktop\\simple-banking-system\\simplebankingsystem\\transactions.csv"))) {
            String[] line;
            int count = 0;
            List<String[]> batch = new ArrayList<>();
            while ((line = reader.readNext()) != null) {
                batch.add(line);
                if (++count % BATCH_SIZE == 0) {
                    executorService.submit(new ImportTask(batch));
                    batch = new ArrayList<>();
                }
            }
            if (!batch.isEmpty()) {
                executorService.submit(new ImportTask(batch));
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            executorService.shutdown();
        }
    }

    class ImportTask implements Runnable{
        private List<String[]> batch;

        public ImportTask(List<String[]> batch) {
            this.batch = batch;
        }
        @Override
        public void run() {
            batch.forEach(line -> {
                Long senderAccountId = Long.parseLong(line[0]);
                Long receiverAccountId = Long.parseLong(line[1]);
                Long senderCustomerId = Long.parseLong(line[2]);
                Long receiverCustomerId = Long.parseLong(line[3]);
                String currency = line[4];
                BigDecimal amount = new BigDecimal(line[5]);
                Date transactionDate = Date.from(LocalDateTime.parse(line[6]).atZone(ZoneId.systemDefault()).toInstant());
                String message = line[7];

                final ImportDto importDto = new ImportDto();
                importDto.setSenderAccountId(senderAccountId);
                importDto.setReceiverAccountId(receiverAccountId);
                importDto.setSenderCustomerId(senderCustomerId);
                importDto.setReceiverCustomerId(receiverCustomerId);
                importDto.setCurrency(ECurrency.get(currency));
                importDto.setAmount(amount);
                importDto.setCreatedAt(transactionDate);
                importDto.setMessage(message);

                final Account sender = accountService.findOrCreateAccount(importDto.getSenderAccountId(), importDto.getSenderCustomerId());
                final Account receiver = accountService.findOrCreateAccount(importDto.getReceiverAccountId(), importDto.getReceiverCustomerId());

                transactionService.importTransaction(importDto, sender, receiver);
            });
        }
    }
}
