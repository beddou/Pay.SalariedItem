package com.pay.salariedItem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pay.salariedItem.model.Mutual;

@Repository
public interface MutualRepository extends JpaRepository<Mutual, Integer> {
    List<Mutual> findByOrganism(int idOrganism);
}
