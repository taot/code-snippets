package com.taot.guice.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "ACCOUNT")
public class Account {
    
    @Id
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "OWNER")
    private String owner;

    @Column(name = "CAPITAL")
    private BigDecimal capital;

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", owner=" + owner + ", capital=" + capital + "]";
    }
}
