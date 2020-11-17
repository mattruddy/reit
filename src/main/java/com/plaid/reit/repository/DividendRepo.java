package com.plaid.reit.repository;

import com.plaid.reit.model.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DividendRepo extends JpaRepository<Dividend, Long> {
    List<Dividend> findAllByInvestorId(long investorId);
}
