package com.pay.salarieditem.business;

import java.util.List;
import java.util.Optional;

import com.pay.salarieditem.model.Mutual;
import com.pay.salarieditem.repository.MutualRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MutualBusiness {

    @Autowired
    private MutualRepository mutualRepository;

    @Value("${SalariedUrl}")
    private String ressourceUrl;

    public List<Mutual> findMutualByOrganism(int idOrganism) {
        return mutualRepository.findByOrganism(idOrganism);

    }

    public Optional<Mutual> getMutual(int id) {
        return mutualRepository.findById(id);
    }

    public Mutual updateMutual(int id, Mutual mutual) {

        Optional<Mutual> mutual1 = mutualRepository.findById(id);
        Mutual mutual2 = new Mutual();
        if (mutual1.isPresent()) {
            mutual2 = mutual1.get();
            if (mutual.getCode() > 0)
                mutual2.setCode(mutual.getCode());

            if (mutual.getDesign() != null && !mutual.getDesign().equals("") && !mutual.getDesign().trim().equals(""))
                mutual2.setDesign(mutual.getDesign());

            if (mutual.getAccountNumber() != null && !mutual.getAccountNumber().equals("")
                    && !mutual.getAccountNumber().trim().equals(""))
                mutual2.setAccountNumber(mutual.getAccountNumber());

            if (mutual.getBank() != null && !mutual.getBank().equals("") && !mutual.getBank().trim().equals(""))
                mutual2.setBank(mutual.getBank());
        }

        return mutualRepository.save(mutual2);

    }

    public Mutual createMutual(Mutual mutual) {
        return mutualRepository.save(mutual);
    }

    public boolean deleteMutual(int idMutual) {

        boolean succes = false;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> httpEntity = HttpEntity.EMPTY;
        ResponseEntity<Boolean> responseEntity = restTemplate
                .exchange(ressourceUrl + "/existByMutual/" + idMutual, HttpMethod.GET, httpEntity, Boolean.class);
        if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value())
            if (responseEntity.hasBody())
                if (responseEntity.getBody().booleanValue()) {

                    mutualRepository.deleteById(idMutual);
                    succes = true;

                }

        return succes;

    }

}
