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

import com.pay.salariedItem.business.SsBusiness;
import com.pay.salariedItem.exception.EntityNotFoundException;
import com.pay.salariedItem.exception.NoEntityAddedException;
import com.pay.salariedItem.model.SS;

@CrossOrigin(origins = "*")
@RestController
public class SsController {

    @Autowired
    private SsBusiness ssBusiness;

    private String ssNotFound = "SS not found";
    private String ssNotSaved = "SS not saved";

    @GetMapping(value = "/SalariedItem/Ss/Get/{idSs}")
    public ResponseEntity<SS> getSs(@PathVariable("idSs") int idSs) {
        Optional<SS> ss = ssBusiness.getSs(idSs);
        if (ss.isPresent())
            return new ResponseEntity<>(ss.get(), HttpStatus.OK);
        else
            throw new EntityNotFoundException(ssNotFound);

    }

    @GetMapping(value = "/SalariedItem/Ss/All/{idOrganism}")
    public List<SS> getSssOfOrganism(@PathVariable("idOrganism") int idOrganism) {
        List<SS> Sss = ssBusiness.findSsByOrganism(idOrganism);
        if (Sss.isEmpty()) {
            throw new EntityNotFoundException(ssNotFound);
        } else
            return Sss;

    }

    @PostMapping(value = "/SalariedItem/Ss/Create")
    public ResponseEntity<SS> createSs(@RequestBody SS ss) {
        try {
            SS ss1 = ssBusiness.createSs(ss);
            return new ResponseEntity<>(ss1, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new NoEntityAddedException(ssNotSaved);
        }

    }

    @PutMapping(value = "/SalariedItem/Ss/Update/{idSs}")
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

    @DeleteMapping(value = "/SalariedItem/Ss/Delete/{idSs}")

    public ResponseEntity<Boolean> deleteSs(@PathVariable("idSs") int idSs) {        

        return new ResponseEntity<>(ssBusiness.deleteSs(idSs), HttpStatus.OK);

    }

}
