package com.pay.salariedItem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pay.salariedItem.model.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer>{
    List<Bank> findByOrganism(int idOrganism);
}
