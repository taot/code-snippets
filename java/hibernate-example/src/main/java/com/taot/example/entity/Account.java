package com.taot.example.entity;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.SQLUpdate;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
@SQLInsert(sql = "insert into ACCOUNT (ID, OWNER, CAPITAL) values (?, ?, ?) on duplicate key update set OWNER = ?, CAPITAL = ?", check = ResultCheckStyle.NONE)
@SQLUpdate(sql = "insert into ACCOUNT (ID, OWNER, CAPITAL) values (?, ?, ?) on duplicate key update set OWNER = ?, CAPITAL = ?", check = ResultCheckStyle.NONE)
public class Account {

    private Long id;
    
    private String owner;
    
    private BigDecimal capital;
    
    public Account() {
        // this one used by hibernate
    }
    
    public Account(String owner, BigDecimal capital) {
        this.owner = owner;
        this.capital = capital;
    }

    @Id
    @Column(name = "ID")
//    @GeneratedValue(strategy = GenerationType.AUTO)
    //@GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "OWNER")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
        return "Account [id=" + id + ", owner=" + owner + ", capital=" + capital + "]";
    }
    
}
