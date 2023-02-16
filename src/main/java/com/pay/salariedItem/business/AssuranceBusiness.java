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

import com.pay.salariedItem.model.Assurance;
import com.pay.salariedItem.repository.AssuranceRepository;

@Service
public class AssuranceBusiness {

    @Autowired
    private AssuranceRepository assuranceRepository;

    @Value("${SalariedUrl}")
    private String ressourceUrl;

    public List<Assurance> findAssuranceByOrganism(int idOrganism) {
        return assuranceRepository.findByOrganism(idOrganism);

    }

    public Optional<Assurance> getAssurance(int id) {
        return assuranceRepository.findById(id);
    }

    public Assurance updateAssurance(int id, Assurance assurance) {

        assurance.setId(id);
        return assuranceRepository.save(assurance);

    }

    public Assurance createAssurance(Assurance assurance) {
        return assuranceRepository.save(assurance);
    }

    public boolean deleteAssurance(int idAssurance) {

        boolean success = false;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> httpEntity = HttpEntity.EMPTY;
        ResponseEntity<Boolean> responseEntity = restTemplate
                .exchange(ressourceUrl + "/existByAssurance/" + idAssurance, HttpMethod.GET, httpEntity, Boolean.class);
        if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value() && responseEntity.hasBody()) {
            boolean response = responseEntity.getBody();
            if (response) {
                try {
                    assuranceRepository.deleteById(idAssurance);
                    success = true;
                } catch (Exception e) {
                    success = false;
                }

            }
        }

        return success;

    }

}
