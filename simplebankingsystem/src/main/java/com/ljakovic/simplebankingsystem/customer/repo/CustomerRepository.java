package com.ljakovic.simplebankingsystem.customer.repo;

import com.ljakovic.simplebankingsystem.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
