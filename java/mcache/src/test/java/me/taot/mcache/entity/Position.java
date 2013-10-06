package me.taot.mcache.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;


abstract public class Position implements Serializable {

    private Long positionId;

    private Long accountId;

    protected Position() {
    }

    public Position(Long positionId, Long accountId) {
        this.positionId = positionId;
        this.accountId = accountId;
    }

    @Id
    @Column
    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    @Column
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
