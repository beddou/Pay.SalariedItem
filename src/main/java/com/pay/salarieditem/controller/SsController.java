package com.pay.salarieditem.controller;

import java.util.List;
import java.util.Optional;

import com.pay.salarieditem.business.SsBusiness;
import com.pay.salarieditem.exception.EntityNotFoundException;
import com.pay.salarieditem.exception.NoEntityAddedException;
import com.pay.salarieditem.model.SS;

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

@CrossOrigin(origins = "*")
@RestController
public class SsController {

    @Autowired
    private SsBusiness ssBusiness;

    private String ssNotFound = "Ss not found"; 
    private String ssNotSaved = "Ss not saved";

    @GetMapping(value = "/SalariedItem/Ss/getSssOfOrganism/{idOrganism}")
    public List<SS> getSssOfOrganism(@PathVariable("idOrganism") int idOrganism) {
        List<SS> Sss = ssBusiness.findSsByOrganism(idOrganism);
        if (Sss.isEmpty()) {
            throw new EntityNotFoundException(ssNotFound);
        } else
            return Sss;

    }

    @PostMapping(value = "/SalariedItem/Ss/create")
    public ResponseEntity<SS> createSs(@RequestBody SS ss) {
        try {
            SS ss1 = ssBusiness.createSs(ss);
            return new ResponseEntity<>(ss1 , HttpStatus.CREATED);
        } catch (Exception e) {
            throw new NoEntityAddedException(ssNotSaved);
        }

    }

    @PutMapping(value = "/SalariedItem/Ss/update/{idSs}")
    public ResponseEntity<SS> upDateSs(@PathVariable("idSs") int idSs,
            @RequestBody SS ss) {

        Optional<SS> ss1 = ssBusiness.getSs(idSs);

        if (ss1.isPresent()) {
            try {

                SS ss2 = ssBusiness.updateSs(idSs, ss);
                return new ResponseEntity<>(ss2, HttpStatus.CREATED);

            } catch (Exception e) {
                throw new NoEntityAddedException(ssNotSaved);
            }

        } else {
            throw new EntityNotFoundException(ssNotFound);

        }

    }

    @DeleteMapping(value = "/SalariedItem/Ss/delete/{idSs}")

    public ResponseEntity<Boolean> deleteSs(@PathVariable("idSs") int idSs) {

        Optional<SS> ss =  ssBusiness.getSs(idSs);
        if (!ss.isPresent())
            throw new EntityNotFoundException(ssNotFound);

        return new ResponseEntity<>( ssBusiness.deleteSs(idSs), HttpStatus.OK);

    }

    
}