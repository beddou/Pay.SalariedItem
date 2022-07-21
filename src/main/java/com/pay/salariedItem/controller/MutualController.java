package com.pay.salariedItem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pay.salariedItem.business.MutualBusiness;
import com.pay.salariedItem.exception.EntityNotFoundException;
import com.pay.salariedItem.exception.NoEntityAddedException;
import com.pay.salariedItem.model.Mutual;

@CrossOrigin(origins = "*")
@RestController
public class MutualController {

    @Autowired
    private MutualBusiness mutualBusiness;

    private String mutualNotFound = "Mutual not found";
    private String mutualNotSaved = "Mutual not saved";

    @GetMapping(value = "/SalariedItem/Mutual/Get/{idMutual}")
    public ResponseEntity<Mutual> getMutual(@PathVariable("idMutual") int idMutual) {
        Optional<Mutual> mutual = mutualBusiness.getMutual(idMutual);
        if (mutual.isPresent())
            return new ResponseEntity<>(mutual.get(), HttpStatus.OK);
        else
            throw new EntityNotFoundException(mutualNotFound);

    }

    @GetMapping(value = "/SalariedItem/Mutual/All/{idOrganism}")
    public List<Mutual> getMutualsOfOrganism(@PathVariable("idOrganism") int idOrganism) {
        List<Mutual> mutuals = mutualBusiness.findMutualByOrganism(idOrganism);
        if (mutuals.isEmpty()) {
            throw new EntityNotFoundException(mutualNotFound);
        } else
            return mutuals;

    }

    @PostMapping(value = "/SalariedItem/Mutual/Create")
    public ResponseEntity<Mutual> createMutual(@RequestBody Mutual mutual) {
        try {
            Mutual mutual1 = mutualBusiness.createMutual(mutual);
            return new ResponseEntity<>(mutual1, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new NoEntityAddedException(mutualNotSaved);
        }

    }

    @PutMapping(value = "/SalariedItem/Mutual/Update/{idMutual}")
    public ResponseEntity<Mutual> upDateMutual(@PathVariable("idMutual") int idMutual,
            @RequestBody Mutual mutual) {

        Optional<Mutual> mutual1 = mutualBusiness.getMutual(idMutual);

        if (mutual1.isPresent()) {
            try {

                Mutual mutual2 = mutualBusiness.updateMutual(idMutual, mutual);
                return new ResponseEntity<>(mutual2, HttpStatus.CREATED);

            } catch (Exception e) {
                throw new NoEntityAddedException(mutualNotSaved);
            }

        } else {
            throw new EntityNotFoundException(mutualNotFound);

        }

    }

    @DeleteMapping(value = "/SalariedItem/Mutual/Delete/{idMutual}")

    public ResponseEntity<Boolean> deleteMutual(@PathVariable("idMutual") int idMutual) {

        return new ResponseEntity<>(mutualBusiness.deleteMutual(idMutual), HttpStatus.OK);

    }

}
