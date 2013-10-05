package types;

import java.util.Map;

public enum AssetClass implements DbType<Long> {

    EQUITY(1L),
    BOND(2L),
    CASH(3L);

    private final long dbValue;

    @Override
    public Long getDbValue() {
        return dbValue;
    }

    public static AssetClass fromDbValue(Long v) {
        AssetClass ac = null;
        for (AssetClass o : AssetClass.values()) {
            if (o.dbValue == v)
                ac = o;
        }
        return ac;
    }

    private AssetClass(long dbValue) {
        this.dbValue = dbValue;
    }
}
