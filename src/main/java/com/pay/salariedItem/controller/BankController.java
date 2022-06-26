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

import com.pay.salariedItem.business.BankBusiness;
import com.pay.salariedItem.exception.EntityNotFoundException;
import com.pay.salariedItem.exception.NoEntityAddedException;
import com.pay.salariedItem.model.Bank;

@CrossOrigin(origins = "*")
@RestController
public class BankController {

    @Autowired
    private BankBusiness bankBusiness;

    private String bankNotFound = "bank not found";
    private String bankNotSaved = "bank not saved";

    @GetMapping(value = "/SalariedItem/Bank/All/{idOrganism}")
    public List<Bank> getBanksOfOrganism(@PathVariable("idOrganism") int idOrganism) {
        List<Bank> banks = bankBusiness.findBankByOrganism(idOrganism);
        if (banks.isEmpty()) {
            throw new EntityNotFoundException(bankNotFound);
        } else
            return banks;

    }

    @PostMapping(value = "/SalariedItem/Bank/Create")
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        try {
            Bank bank1 = bankBusiness.createBank(bank);
            return new ResponseEntity<>(bank1, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new NoEntityAddedException(bankNotSaved);
        }

    }

    @PutMapping(value = "/SalariedItem/Bank/Update/{idBank}")
    public ResponseEntity<Bank> upDateBank(@PathVariable("idBank") int idBank,
            @RequestBody Bank bank) {

        Optional<Bank> bank1 = bankBusiness.getBank(idBank);

        if (bank1.isPresent()) {
            try {

                Bank bank2 = bankBusiness.updateBank(idBank, bank);
                return new ResponseEntity<>(bank2, HttpStatus.CREATED);

            } catch (Exception e) {
                throw new NoEntityAddedException(bankNotSaved);
            }

        } else {
            throw new EntityNotFoundException(bankNotFound);

        }

    }

    @DeleteMapping(value = "/SalariedItem/Bank/Delete/{idBank}")

    public ResponseEntity<Boolean> deleteBank(@PathVariable("idBank") int idBank) {

        Optional<Bank> bank = bankBusiness.getBank(idBank);
        if (!bank.isPresent())
            throw new EntityNotFoundException(bankNotFound);

        return new ResponseEntity<>(bankBusiness.deleteBank(idBank), HttpStatus.OK);

    }
}
