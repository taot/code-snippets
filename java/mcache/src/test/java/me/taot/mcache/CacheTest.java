package me.taot.mcache;

import junit.framework.Assert;
import me.taot.mcache.entity.SecurityPosition;
import static me.taot.mcache.Dsl.transaction;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CacheTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testSimplePutGetRemove() {
        final SecurityPositionCache cache = new SecurityPositionCache();
        final SecurityPosition position = new SecurityPosition(1L, 2L, 3L, 4L);

        // add to cache
        transaction(new Runnable() {
            public void run() {
                cache.put(position.getPositionId(), position);
            }
        });

        // check position in cache
        transaction(new Runnable() {
            public void run() {
                SecurityPosition p = cache.get(position.getPositionId());
                Assert.assertTrue(position != p);
                Assert.assertEquals(position, p);
            }
        });

        // find by index
        transaction(new Runnable() {
            public void run() {
                List<SecurityPosition> list = cache.findByAccountIdLedgerId(position.getAccountId(), position.getLedgerId());
                Assert.assertEquals(1, list.size());
                SecurityPosition p = list.get(0);
                Assert.assertTrue(position != p);
                Assert.assertEquals(position, p);
            }
        });

        // update in cache
        position.setAccountId(102L);
        position.setLedgerId(103L);
        position.setSecurityId(104L);
        transaction(new Runnable() {
            public void run() {
                cache.put(position.getPositionId(), position);
            }
        });

        // check position is updated
        transaction(new Runnable() {
            public void run() {
                SecurityPosition p = cache.get(position.getPositionId());
                Assert.assertTrue(position != p);
                Assert.assertEquals(position, p);
            }
        });

        // find by index again
        transaction(new Runnable() {
            public void run() {
                List<SecurityPosition> list = cache.findByAccountIdLedgerId(position.getAccountId(), position.getLedgerId());
                Assert.assertEquals(1, list.size());
                SecurityPosition p = list.get(0);
                Assert.assertTrue(position != p);
                Assert.assertEquals(position, p);
            }
        });

        // remve from cache
        transaction(new Runnable() {
            public void run() {
                cache.remove(position.getPositionId());
            }
        });

        // check it's removed
        transaction(new Runnable() {
            public void run() {
                SecurityPosition p = cache.get(position.getPositionId());
                Assert.assertNull(p);
            }
        });
    }

    @Test
    public void testManyObjects() {
        final long NUM = 1000 * 1000;
        final SecurityPositionCache cache = new SecurityPositionCache();

        // add to cache
        transaction(new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();

                for (long i = 0; i < NUM; i++) {
                    SecurityPosition p = new SecurityPosition(i, i, i, i);
                    cache.put(p.getPositionId(), p);
                    if ((i + 1) % (1000 * 100) == 0) {
                        logger.debug("Object count: {}", (i + 1));
                    }
                }

                long duration = System.currentTimeMillis() - start;
                logger.info("Creating {} objects and add to cache: {}ms", NUM, duration);
            }
        });

        // get from cache
        transaction(new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();

                for (long i = 0; i < NUM; i++) {
                    SecurityPosition p = cache.get(i);
                    Assert.assertNotNull(p);
                    Assert.assertEquals((Long) i, p.getPositionId());
                    Assert.assertEquals((Long) i, p.getAccountId());
                    Assert.assertEquals((Long) i, p.getLedgerId());
                    Assert.assertEquals((Long) i, p.getSecurityId());
                }

                long duration = System.currentTimeMillis() - start;
                logger.info("Getting {} objects: {}ms", NUM, duration);
            }
        });

        // find by index
        transaction(new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();

                for (long i = 0; i < NUM; i++) {
                    List<SecurityPosition> list = cache.findByAccountIdLedgerId(i, i);
                    Assert.assertEquals(1, list.size());
                    SecurityPosition p = list.get(0);
                    Assert.assertNotNull(p);
                    Assert.assertEquals((Long) i, p.getPositionId());
                    Assert.assertEquals((Long) i, p.getAccountId());
                    Assert.assertEquals((Long) i, p.getLedgerId());
                    Assert.assertEquals((Long) i, p.getSecurityId());
                }

                long duration = System.currentTimeMillis() - start;
                logger.info("Finding {} objects by index: {}ms", NUM, duration);
            }
        });

        // update objects
        transaction(new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();

                for (long i = 0; i < NUM; i++) {
                    SecurityPosition p = cache.get(i);
                    p.setAccountId(i + 1);
                    p.setLedgerId(i + 2);
                    p.setSecurityId(i + 3);
                    cache.put(p.getPositionId(), p);

                    SecurityPosition np = cache.get(i);
                    Assert.assertNotNull(np);
                    Assert.assertEquals((Long) i, np.getPositionId());
                    Assert.assertEquals((Long) (i + 1), np.getAccountId());
                    Assert.assertEquals((Long) (i + 2), np.getLedgerId());
                    Assert.assertEquals((Long) (i + 3), np.getSecurityId());
                }

                long duration = System.currentTimeMillis() - start;
                logger.info("Updating {} objects: {}ms", NUM, duration);
            }
        });

        // find by index after update
        transaction(new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();

                for (long i = 0; i < NUM; i++) {
                    List<SecurityPosition> list = cache.findByAccountIdLedgerId(i + 1, i + 2);
                    Assert.assertEquals(1, list.size());
                    SecurityPosition p = list.get(0);
                    Assert.assertNotNull(p);
                    Assert.assertEquals((Long) i, p.getPositionId());
                    Assert.assertEquals((Long) (i + 1), p.getAccountId());
                    Assert.assertEquals((Long) (i + 2), p.getLedgerId());
                    Assert.assertEquals((Long) (i + 3), p.getSecurityId());
                }

                long duration = System.currentTimeMillis() - start;
                logger.info("Finding {} objects by index after update: {}ms", NUM, duration);
            }
        });

        // remove objects
        transaction(new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();

                for (long i = 0; i < NUM; i++) {
                    cache.remove(i);
                    SecurityPosition p = cache.get(i);
                    Assert.assertNull(p);
                }

                long duration = System.currentTimeMillis() - start;
                logger.info("Removing {} objects: {}ms", NUM, duration);
            }
        });
    }

    @Test
    public void testMultiThread() {
        final long OBJECT_NUM = 1000;
        final long UPDATE_NUM = 10;
        final long SLEEP_INTERVAL = 100;
        final SecurityPositionCache cache = new SecurityPositionCache();

        // add initial data to cache
        transaction(new Runnable() {
            public void run() {
                for (long i = 0; i < OBJECT_NUM; i++) {
                    SecurityPosition p = new SecurityPosition(i, i, i, i);
                    cache.put(p.getPositionId(), p);
                }
            }
        });

        Runnable writer = new Runnable() {
            public void run() {
                transaction(new Runnable() {
                    public void run() {
                        logger.info("Writer thread started.");
                        for (long i = 0; i < UPDATE_NUM; i++) {
                            logger.info("Writer updating object #{}...", i);
                            SecurityPosition p = cache.get(i);
                            p.setAccountId(i + 1);
                            p.setLedgerId(i + 2);
                            p.setSecurityId(i + 3);
                            cache.put(i, p);
                            sleep("Writer", SLEEP_INTERVAL);
                        }
                    }
                });
            }
        };

        Runnable reader = new Runnable() {
            public void run() {
                transaction(new Runnable() {
                    public void run() {
                        logger.info("Reader thread started.");
                        sleep("Reader", SLEEP_INTERVAL / 2);
                        for (long i = 0; i < UPDATE_NUM; i++) {
                            logger.info("Reader checking object #{}", i);
                            SecurityPosition p = cache.get(i);
                            Assert.assertEquals((Long) i, p.getPositionId());
                            Assert.assertEquals((Long) (i + 1), p.getAccountId());
                            Assert.assertEquals((Long) (i + 2), p.getLedgerId());
                            Assert.assertEquals((Long) (i + 3), p.getSecurityId());
                            sleep("Reader", SLEEP_INTERVAL);
                        }
                    }
                });
            }
        };

        Thread t0 = new Thread(writer);
        Thread t1 = new Thread(reader);
        t0.start();
        t1.start();
        try {
            t0.join();
            t1.join();
        } catch (InterruptedException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    private void sleep(String name, long millis) {
        logger.info("{} sleeping {}ms...", name, millis);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
