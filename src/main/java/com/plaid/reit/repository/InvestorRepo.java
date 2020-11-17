package com.plaid.reit.repository;

import com.plaid.reit.model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestorRepo extends JpaRepository<Investor, Long> {
    Investor findByEndUserId(long endUserId);
}
