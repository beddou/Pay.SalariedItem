package com.pay.salariedItem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pay.salariedItem.model.SS;

@Repository
public interface SsRepository extends JpaRepository<SS, Integer> {
    List<SS> findByOrganism(int idOrganism);
    
}
