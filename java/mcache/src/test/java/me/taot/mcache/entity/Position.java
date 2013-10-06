package me.taot.mcache.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;


abstract public class Position implements Serializable {

    private Long positionId;

    private Long accountId;

    private Long ledgerId;

    protected Position() {
    }

    public Position(Long positionId, Long accountId, Long ledgerId) {
        this.positionId = positionId;
        this.accountId = accountId;
        this.ledgerId = ledgerId;
    }

    @Id
    @Column
    public Long getPositionId() {
        return positionId;
    }

    private void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    @Column
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Column
    public Long getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(Long ledgerId) {
        this.ledgerId = ledgerId;
    }
}
