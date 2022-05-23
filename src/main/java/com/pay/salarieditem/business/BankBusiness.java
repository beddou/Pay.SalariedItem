package com.pay.salarieditem.business;

import java.util.List;
import java.util.Optional;

import com.pay.salarieditem.model.Bank;
import com.pay.salarieditem.repository.BankRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BankBusiness {

    @Autowired
    private BankRepository bankRepository;

    @Value("${SalariedUrl}")
    private String ressourceUrl;

    public List<Bank> findBankByOrganism(int idOrganism) {
        return bankRepository.findByOrganism(idOrganism);

    }

    public Optional<Bank> getBank(int id) {
        return bankRepository.findById(id);
    }

    public Bank updateBank(int id, Bank bank) {

        Bank bank1 = bankRepository.findById(id).get();

        if (bank.getCode() > 0)
            bank1.setCode(bank.getCode());

        if (bank.getDesign() != null && !bank.getDesign().equals("") && !bank.getDesign().trim().equals(""))
            bank1.setDesign(bank.getDesign());

        if (bank.getAccountNumber() != null && !bank.getAccountNumber().equals("")
                && !bank.getAccountNumber().trim().equals(""))
            bank1.setAccountNumber(bank.getAccountNumber());

        if (bank.getMandateModel() != null)
            bank1.setMandateModel(bank.getMandateModel());

        System.out.println(bank1.toString());
        return bankRepository.save(bank1);

    }

    public Bank createBank(Bank bank) {
        return bankRepository.save(bank);
    }

    public boolean deleteBank(int idBank) {

        boolean succes = false;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> httpEntity = HttpEntity.EMPTY;
        ResponseEntity<Boolean> responseEntity = restTemplate
                .exchange(ressourceUrl + "/existByBank/" + idBank, HttpMethod.GET, httpEntity, Boolean.class);
        if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value())
            if (responseEntity.hasBody())
                if (responseEntity.getBody().booleanValue()) {

                    bankRepository.deleteById(idBank);
                    succes = true;

                }

        return succes;

    }

}
