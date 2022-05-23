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

        Mutual mutual1 = mutualRepository.findById(id).get();

        if (mutual.getCode() > 0)
            mutual1.setCode(mutual.getCode());
            
        if (mutual.getDesign() != null && !mutual.getDesign().equals("") && !mutual.getDesign().trim().equals(""))
            mutual1.setDesign(mutual.getDesign());

        if (mutual.getAccountNumber() != null && !mutual.getAccountNumber().equals("")
                && !mutual.getAccountNumber().trim().equals(""))
            mutual1.setAccountNumber(mutual.getAccountNumber());

        if (mutual.getBank() != null && !mutual.getBank().equals("") && !mutual.getBank().trim().equals(""))
            mutual1.setBank(mutual.getBank());

        return mutualRepository.save(mutual1);

    }

    public Mutual createMutual(Mutual mutual) {
        return mutualRepository.save(mutual);
    }

    public boolean deleteMutual(int idMutual) {

        boolean succes = false;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> httpEntity = HttpEntity.EMPTY;
        ResponseEntity<Boolean> responseEntity = restTemplate
                .exchange(ressourceUrl+"/existByMutual/" + idMutual, HttpMethod.GET, httpEntity, Boolean.class);
        if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value())
            if (responseEntity.hasBody())
                if (responseEntity.getBody().booleanValue()) {

                    mutualRepository.deleteById(idMutual);
                    succes = true;

                }

        return succes;

    }

}
