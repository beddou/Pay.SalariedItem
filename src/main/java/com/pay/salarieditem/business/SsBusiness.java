package com.pay.salarieditem.business;

import java.util.List;
import java.util.Optional;

import com.pay.salarieditem.model.SS;
import com.pay.salarieditem.repository.SsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SsBusiness {

    @Autowired
    private SsRepository ssRepository;

    @Value("${SalariedUrl}")
    private String ressourceUrl;

    public List<SS> findSsByOrganism(int idOrganism) {
        return ssRepository.findByOrganism(idOrganism);

    }

    public Optional<SS> getSs(int id) {
        return ssRepository.findById(id);
    }

    public SS updateSs(int id, SS ss) {

        Optional<SS> ss1 = ssRepository.findById(id);
        SS ss2 = new SS();
        if (ss1.isPresent()) {
            ss2 = ss1.get();
            if (ss.getCode() > 0)
                ss2.setCode(ss.getCode());

            if (ss.getDesign() != null && !ss.getDesign().equals("") && !ss.getDesign().trim().equals(""))
                ss2.setDesign(ss.getDesign());

            if (ss.getAccountNumber() != null && !ss.getAccountNumber().equals("")
                    && !ss.getAccountNumber().trim().equals(""))
                ss2.setAccountNumber(ss.getAccountNumber());

            if (ss.getBank() != null && !ss.getBank().equals("") && !ss.getBank().trim().equals(""))
                ss2.setBank(ss.getBank());

        }

        return ssRepository.save(ss2);

    }

    public SS createSs(SS ss) {
        return ssRepository.save(ss);
    }

    public boolean deleteSs(int idSs) {

        boolean succes = false;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> httpEntity = HttpEntity.EMPTY;
        ResponseEntity<Boolean> responseEntity = restTemplate
                .exchange(ressourceUrl + "/existBySs/" + idSs, HttpMethod.GET, httpEntity, Boolean.class);
        if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value())
            if (responseEntity.hasBody())
                if (responseEntity.getBody().booleanValue()) {

                    ssRepository.deleteById(idSs);
                    succes = true;

                }

        return succes;

    }

}
