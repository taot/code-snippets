package me.taot.mcache;

import me.taot.mcache.entity.SecurityPosition;
import me.taot.mcache.util.EntityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SecurityPositionCache extends AbstractCache<SecurityPosition, Long> {

    private final ConcurrentMap<AccountLedgerIndexKey, List<Element<SecurityPosition>>> accountLedgerIndex = new ConcurrentHashMap<>();

    public List<SecurityPosition> findByAccountIdLedgerId(Long accountId, Long ledgerId) {
        AccountLedgerIndexKey key = new AccountLedgerIndexKey(accountId, ledgerId);
        List<Element<SecurityPosition>> list = accountLedgerIndex.get(key);
        if (list == null) {
            return Collections.emptyList();
        } else {
            List<SecurityPosition> newList = new ArrayList<>();
            for (Element<SecurityPosition> e : list) {
                e.getLock().lockRead();
                TransactionManager.current().addLock(e.getLock());
                SecurityPosition newP = EntityUtil.clone(e.getValue());
                newList.add(newP);
            }
            return newList;
        }
    }

    @Override
    protected void createIndexes(Element<SecurityPosition> e) {
        if (e == null || e.getValue() == null) {
            return;
        }
        AccountLedgerIndexKey key = new AccountLedgerIndexKey(e.getValue().getAccountId(), e.getValue().getLedgerId());
        List<Element<SecurityPosition>> list = getAccountLedgerIndexList(key);
        list.add(e);
    }

    @Override
    protected void removeIndexes(Element<SecurityPosition> e) {
        if (e == null || e.getValue() == null) {
            return;
        }
        AccountLedgerIndexKey key = new AccountLedgerIndexKey(e.getValue().getAccountId(), e.getValue().getLedgerId());
        List<Element<SecurityPosition>> list = getAccountLedgerIndexList(key);
        for (Iterator<Element<SecurityPosition>> iter = list.iterator(); iter.hasNext(); ) {
            Element<SecurityPosition> elem = iter.next();
            if (e == elem) {
                iter.remove();
            }
        }
        if (list.isEmpty()) {
            accountLedgerIndex.remove(key);
        }
    }

    private List<Element<SecurityPosition>> getAccountLedgerIndexList(AccountLedgerIndexKey key) {
        List<Element<SecurityPosition>> list = accountLedgerIndex.get(key);
        if (list == null) {
            List<Element<SecurityPosition>> newList = new ArrayList<>();
            list = accountLedgerIndex.putIfAbsent(key, newList);
            if (list == null) {
                list = newList;
            }
        }
        return list;
    }


    /*
     * AccountLedgerIndexKey class
     */
    private static class AccountLedgerIndexKey {

        final Long accountId;
        final Long ledgerId;

        AccountLedgerIndexKey(Long accountId, Long ledgerId) {
            this.accountId = accountId;
            this.ledgerId = ledgerId;
        }

        private Long getAccountId() {
            return accountId;
        }

        private Long getLedgerId() {
            return ledgerId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AccountLedgerIndexKey that = (AccountLedgerIndexKey) o;

            if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) return false;
            if (ledgerId != null ? !ledgerId.equals(that.ledgerId) : that.ledgerId != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = accountId != null ? accountId.hashCode() : 0;
            result = 31 * result + (ledgerId != null ? ledgerId.hashCode() : 0);
            return result;
        }
    }
}
