package com.pay.salarieditem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueSS", columnNames = { "organism", "code" }) })
public class SS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1, message = "The code must be input")
    private int code;

    @NotNull(message = "Name cannot be null")
    @NotEmpty
    @NotBlank
    private String design;

    @Pattern(regexp="\\d+") 
    private String accountNumber;
    
    private String bank;

    @Min(value = 1, message = "The organism must be input")
    private int organism;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public int getOrganism() {
        return organism;
    }

    public void setOrganism(int organism) {
        this.organism = organism;
    }

    
    
}
