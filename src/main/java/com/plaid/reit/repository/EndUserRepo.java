package com.plaid.reit.repository;

import com.plaid.reit.model.EndUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndUserRepo extends JpaRepository<EndUser, Long> {
    EndUser findByEmailIgnoreCase(String email);
}
