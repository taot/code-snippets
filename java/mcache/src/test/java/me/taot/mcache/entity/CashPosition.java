package me.taot.mcache.entity;

import javax.persistence.Column;

public class CashPosition extends Position {

    private String currency;

    protected CashPosition() {
    }

    public CashPosition(Long positionId, Long accountId, String currency) {
        super(positionId, accountId);
        this.currency = currency;
    }

    @Column
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof CashPosition)) {
            return false;
        }
        CashPosition other = (CashPosition) o;
        return this.getPositionId() == other.getPositionId() &&
                this.getAccountId() == other.getAccountId() &&
                stringEquals(this.getCurrency(), other.getCurrency());
    }

    private static boolean stringEquals(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1 != null) {
            return s1.equals(s2);
        }
        return false;
    }
}
