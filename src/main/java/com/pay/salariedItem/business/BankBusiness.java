package com.pay.salariedItem.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pay.salariedItem.model.Bank;
import com.pay.salariedItem.repository.BankRepository;

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

       bank.setId(id);        
       return bankRepository.save(bank);

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
        if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value() && responseEntity.hasBody()) {
            boolean response = responseEntity.getBody();
            if (response) {
                try {
                    bankRepository.deleteById(idBank);
                    succes = true;
                } catch (Exception e) {
                    succes = false;
                }

            }

        }

        return succes;

    }

}
