package com.pay.salarieditem.repository;

import java.util.List;

import com.pay.salarieditem.model.SS;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SsRepository extends JpaRepository<SS, Integer> {
    List<SS> findByOrganism(int idOrganism);
    
}
