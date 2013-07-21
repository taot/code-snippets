package com.taot.example.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="SEC_POSITION")
@PrimaryKeyJoinColumn(name="ID")
public class SecurityPosition extends Position {

    private String securityName;
    
    private BigDecimal costBase;
    
    private BigDecimal quantity;
    
    protected SecurityPosition() {
        // for hibernate
    }
    
    public SecurityPosition(Long accountId, String securityName, BigDecimal costBase, BigDecimal quantity) {
        super(accountId, "SECURITY");
        this.securityName = securityName;
        this.costBase = costBase;
        this.quantity = quantity;
    }

    @Column(name = "SECURITY_NAME")
    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    @Column(name = "COST_BASE")
    public BigDecimal getCostBase() {
        return costBase;
    }

    public void setCostBase(BigDecimal costBase) {
        this.costBase = costBase;
    }

    @Column(name = "QUANTITY")
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SecurityPosition [securityName=" + securityName + ", costBase=" + costBase
                        + ", quantity=" + quantity + ", getId()=" + getId() + ", getAccountId()="
                        + getAccountId() + ", getType()=" + getType() + "]";
    }
    
}
