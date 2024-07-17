package com.ljakovic.simplebankingsystem.customer.repo;

import com.ljakovic.simplebankingsystem.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
