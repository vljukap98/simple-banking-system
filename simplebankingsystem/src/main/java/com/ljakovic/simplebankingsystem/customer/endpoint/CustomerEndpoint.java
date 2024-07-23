package com.ljakovic.simplebankingsystem.customer.endpoint;

import com.ljakovic.simplebankingsystem.customer.dto.CustomerDto;
import com.ljakovic.simplebankingsystem.customer.service.CustomerService;
import com.ljakovic.simplebankingsystem.transaction.dto.TransactionDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customer")
public class CustomerEndpoint {

    private final CustomerService customerService;

    public CustomerEndpoint(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerDto> getCustomerDetails(@PathVariable("id") Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerDetails(customerId));
    }

    @GetMapping(
            value = "/history/{id}"
    )
    public ResponseEntity<List<TransactionDto>> getCustomerTransactionHistory(@PathVariable("id") Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerTransactionHistory(customerId));
    }

    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.createCustomer(customerDto));
    }
}
