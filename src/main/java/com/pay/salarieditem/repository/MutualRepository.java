package com.pay.salarieditem.repository;

import java.util.List;

import com.pay.salarieditem.model.Mutual;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MutualRepository extends JpaRepository<Mutual, Integer> {
    List<Mutual> findByOrganism(int idOrganism);
}
