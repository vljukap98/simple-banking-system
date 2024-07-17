package com.ljakovic.simplebankingsystem.account.repo;

import com.ljakovic.simplebankingsystem.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
