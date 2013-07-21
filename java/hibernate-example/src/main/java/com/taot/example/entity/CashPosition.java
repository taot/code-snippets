package com.taot.example.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="CASH_POSITION")
@PrimaryKeyJoinColumn(name="ID")
public class CashPosition extends Position {

    private BigDecimal capital;
    
    protected CashPosition() {
        // for hibernate
    }
    
    public CashPosition(Long accountId, BigDecimal capital) {
        super(accountId, "CASH");
        this.capital = capital;
    }

    @Column(name = "CAPITAL")
    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    @Override
    public String toString() {
        return "CashPosition [capital=" + capital + ", getId()=" + getId() + ", getAccountId()="
                        + getAccountId() + ", getType()=" + getType() + "]";
    }
    
}
