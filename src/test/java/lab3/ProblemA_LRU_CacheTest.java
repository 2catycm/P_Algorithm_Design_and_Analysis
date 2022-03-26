package lab3;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import random.JudgeRandom;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ProblemA_LRU_CacheTest {
    private static final Logger logger = Logger.getLogger("lab3.ProblemA_LRU_CacheTest");

    @RepeatedTest(100)
    @Timeout(1)
    void findExceptionWhenLarge() {
        logger.entering("lab3.ProblemA_LRU_CacheTest", "findExceptionWhenLarge");
        logger.info("Testing Lab3 ProblemA by finding Exception When Large");
        final var M = JudgeRandom.randomInt(1, (int) 1e4);
        final var N = JudgeRandom.randomInt(1, (int) 1e4);
        final var K = JudgeRandom.randomInt(1, (int) 1e5);
        logger.info("M, N, K has been chosen by random. ");
        logger.info(String.format("M=%d, N=%d, K=%d", M, N, K));
        final var cache = new InnoDBLikeCache<Integer, Integer>(M, N);
        logger.info("cache object has been newed. ");
        int op = 0, key = 0, value = 0;
        int toStringCnt = 0;
        try {
            for (int i = 0; i < K; i++) {
                do{
                    op = JudgeRandom.randomInt(1, 3);
                }while (op==3&&toStringCnt>50);
                switch (op) {
                    case 1: {
                        key = JudgeRandom.randomInt(1, (int) 1e9);
                        value = JudgeRandom.randomInt(1, (int) 1e9);
                        cache.put(key, value);
                        logger.fine("Putting " + key + ":" + value);
                        break;
                    }
                    case 2: {
                        key = JudgeRandom.randomInt(1, (int) 1e9);
                        value = cache.getOrDefault(key, -1);
                        logger.fine("Getting " + key + ":" + value);
                        break;
                    }
                    case 3: {
                        logger.fine("ToString: " + cache.toString());
                        toStringCnt++;
                        break;
                    }
                    default:
                        throw new RuntimeException("");
                }
            }
        } catch (Exception e) {
            logger.severe("Failed! ");
            switch (op) {
                case 1: {
                    logger.severe("Putting " + key + ":" + value);
                    logger.severe("When Cache is: " + cache.toString());
                    break;
                }
                case 2: {
                    logger.severe("Getting " + key + ":" + value);
                    logger.severe("When Cache is: " + cache.toString());
                    break;
                }
                case 3: {
                    logger.severe("When trying ToString");
                    break;
                }
            }
            logger.throwing("lab3.ProblemA_LRU_CacheTest", "findExceptionWhenLarge", e);
            throw e;
        }
        logger.exiting("lab3.ProblemA_LRU_CacheTest", "findExceptionWhenLarge");
    }

    @Test
    void testWhenYoungFull() {
        final var cache = new InnoDBLikeCache<Integer, Integer>(2, 2);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);
        cache.put(3, 3);
        assertEquals("1:1 2:2 3:3", cache.toString().trim());
        cache.get(3);
        assertEquals("3:3 1:1 2:2", cache.toString().trim());
        cache.put(4, 4);
        assertEquals("3:3 1:1 4:4 2:2", cache.toString().trim());
        cache.put(5, 5);
        assertEquals("3:3 1:1 5:5 4:4", cache.toString().trim());
    }

    @Test
    void sample1() {
        final var cache = new InnoDBLikeCache<Integer, Integer>(2, 2);
        cache.put(1, 1);
        cache.put(2, 2);
        assertEquals(1, cache.get(1));
        cache.put(3, 3);
        cache.put(4, 4);
        assertEquals("1:1 2:2 4:4 3:3", cache.toString().trim());
        cache.put(5, 5);
        assertEquals(-1, cache.getOrDefault(3, -1));
        cache.put(5, 4);
        assertEquals("5:4 1:1 2:2 4:4", cache.toString().trim());
    }
}