package me.taot.mcache.util;

import me.taot.mcache.entity.CashPosition;
import me.taot.mcache.entity.SecurityPosition;
import org.junit.Assert;
import org.junit.Test;

public class CloneUtilTest {

    @Test
    public void testCloneSecurityPosition() {
        SecurityPosition position = new SecurityPosition(1L, 2L, 3L);
        SecurityPosition cloned = CloneUtil.clone(position);
        Assert.assertTrue(position != cloned);
        Assert.assertEquals(position, cloned);
    }

    @Test
    public void testCloneCashPosition() {
        CashPosition position = new CashPosition(1L, 2L, "CNY");
        CashPosition cloned = CloneUtil.clone(position);
        Assert.assertTrue(position != cloned);
        Assert.assertEquals(position, cloned);
    }
}
