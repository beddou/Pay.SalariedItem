package com.pay.salariedItem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pay.salariedItem.model.Assurance;

@Repository
public interface AssuranceRepository extends JpaRepository<Assurance, Integer> {
    List<Assurance> findByOrganism(int idOrganism);
    
}
