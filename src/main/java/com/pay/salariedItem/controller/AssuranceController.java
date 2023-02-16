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

import com.pay.salariedItem.business.AssuranceBusiness;
import com.pay.salariedItem.exception.EntityNotFoundException;
import com.pay.salariedItem.exception.NoEntityAddedException;
import com.pay.salariedItem.model.Assurance;

@CrossOrigin(origins = "*")
@RestController
public class AssuranceController {

    @Autowired
    private AssuranceBusiness assuranceBusiness;

    private String assuranceNotFound = "Assurance not found";
    private String assuranceNotSaved = "Assurance not saved";

    @GetMapping(value = "/SalariedItem/Assurance/Get/{idAssurance}")
    public ResponseEntity<Assurance> getAssurance(@PathVariable("idAssurance") int idAssurance) {
        Optional<Assurance> assurance = assuranceBusiness.getAssurance(idAssurance);
        if (assurance.isPresent())
            return new ResponseEntity<>(assurance.get(), HttpStatus.OK);
        else
            throw new EntityNotFoundException(assuranceNotFound);

    }

    @GetMapping(value = "/SalariedItem/Assurance/All/{idOrganism}")
    public List<Assurance> getAssurancesOfOrganism(@PathVariable("idOrganism") int idOrganism) {
        List<Assurance> assurances = assuranceBusiness.findAssuranceByOrganism(idOrganism);
        if (assurances.isEmpty()) {
            throw new EntityNotFoundException(assuranceNotFound);
        } else
            return assurances;

    }

    @PostMapping(value = "/SalariedItem/Assurance/Create")
    public ResponseEntity<Assurance> createAssurance(@RequestBody Assurance assurance) {
        try {
            Assurance assurance1 = assuranceBusiness.createAssurance(assurance);
            return new ResponseEntity<>(assurance1, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new NoEntityAddedException(assuranceNotSaved);
        }

    }

    @PutMapping(value = "/SalariedItem/Assurance/Update/{idAssurance}")
    public ResponseEntity<Assurance> upDateAssurance(@PathVariable("idAssurance") int idAssurance,
            @RequestBody Assurance assurance) {

        Optional<Assurance> assurance1 = assuranceBusiness.getAssurance(idAssurance);

        if (assurance1.isPresent()) {
            try {

                Assurance assurance2 = assuranceBusiness.updateAssurance(idAssurance, assurance);
                return new ResponseEntity<>(assurance2, HttpStatus.CREATED);

            } catch (Exception e) {
                throw new NoEntityAddedException(assuranceNotSaved);
            }

        } else {
            throw new EntityNotFoundException(assuranceNotFound);

        }

    }

    @DeleteMapping(value = "/SalariedItem/Assurance/Delete/{idAssurance}")

    public ResponseEntity<Boolean> deleteAssurance(@PathVariable("idAssurance") int idAssurance) {        

        return new ResponseEntity<>(assuranceBusiness.deleteAssurance(idAssurance), HttpStatus.OK);

    }

}
