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

import com.pay.salariedItem.model.Mutual;
import com.pay.salariedItem.repository.MutualRepository;

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

        mutual.setId(id);
        return mutualRepository.save(mutual);

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
        if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value() && responseEntity.hasBody()) {
            boolean response = responseEntity.getBody();
            if (response) {
                try {
                    mutualRepository.deleteById(idMutual);
                    succes = true;
                } catch (Exception e) {
                    succes = false;
                }

            }
        }

        return succes;

    }

}
