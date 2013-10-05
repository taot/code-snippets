package test;

import java.io.Serializable;

public class Position implements Serializable {

    private Long positionId;

    private Long accountId;

    private Long securityId;

    private String currency;

    private Long quantity;

    public Position(Long positionId, Long accountId, Long securityId, String currency, Long quantity) {
        this.positionId = positionId;
        this.accountId = accountId;
        this.securityId = securityId;
        this.currency = currency;
        this.quantity = quantity;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getSecurityId() {
        return securityId;
    }

    public void setSecurityId(Long securityId) {
        this.securityId = securityId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Position{" +
                "positionId=" + positionId +
                ", accountId=" + accountId +
                ", securityId=" + securityId +
                ", currency='" + currency + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
