package lab3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProblemA_LRU_CacheTest {
    @Test
    void sample1(){
        final var cache = new InnoDBLikeCache<Integer, Integer>(2, 2);
        cache.put(1,1);
        cache.put(2,2);
        assertEquals(1, cache.get(1));
    }
}