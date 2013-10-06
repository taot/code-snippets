package me.taot.mcache.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class SecurityPosition extends Position {

    private Long securityId;

    protected SecurityPosition() {
    }

    public SecurityPosition(Long positionId, Long accountId, Long securityId) {
        super(positionId, accountId);
        this.securityId = securityId;
    }

    @Column
    public Long getSecurityId() {
        return securityId;
    }

    public void setSecurityId(Long securityId) {
        this.securityId = securityId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof SecurityPosition)) {
            return false;
        }
        SecurityPosition other = (SecurityPosition) o;
        return this.getPositionId() == other.getPositionId() &&
                this.getAccountId() == other.getAccountId() &&
                this.getSecurityId() == other.getSecurityId();
    }
}
