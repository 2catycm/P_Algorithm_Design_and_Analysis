package lab3;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import random.JudgeRandom;

import static org.junit.jupiter.api.Assertions.*;

class NumberTheoryToolBoxTest {

    @Test
    void testFastInverse_veryBig() {
        int a = Integer.MAX_VALUE; //2e31-1
        assertEquals(255, NumberTheoryToolBox.fastPower(a, a, 257));
    }
    @Test
    void testFastInverse_easyFermat(){
        assertEquals(1, NumberTheoryToolBox.fastPower(Integer.MAX_VALUE, 256, 257));
        assertEquals(1, NumberTheoryToolBox.fastPower(40, 256, 17));
        assertEquals(1, NumberTheoryToolBox.fastPower(40, 1<<28, 17));
    }
    @Test
    void testFastInverse_easy(){
        assertEquals(81, NumberTheoryToolBox.fastPower(3, 4, 257));
        assertEquals(40, NumberTheoryToolBox.fastPower(9, 2, 41));
    }

    @Test
    void testPrimeModularInverse_easy(){
        assertEquals(4, NumberTheoryToolBox.primeModularInverse(2, 7));
        assertEquals(2, NumberTheoryToolBox.primeModularInverse(4, 7));
        assertEquals(9, NumberTheoryToolBox.primeModularInverse(2, 17));
    }
    @Test
    void testPrimeModularInverse_negative(){
        assertEquals(4, NumberTheoryToolBox.primeModularInverse(-5, 7));
        assertEquals(2, NumberTheoryToolBox.primeModularInverse(-3, 7));
        assertEquals(9, NumberTheoryToolBox.primeModularInverse(-15, 17));
    }

    @RepeatedTest(100)
    void primeModularInverse() {
        final var modulus = JudgeRandom.intRandomPrime();
        final var a = JudgeRandom.randomInt((int) -1e9, (int) 1e9);
        final var inv = NumberTheoryToolBox.primeModularInverse(a, modulus);
        assertEquals(1, NumberTheoryToolBox.normalizeMod((int) ((long)a*inv%modulus), modulus), String.format("when a=%d, calculated inv=%d. \n where prime=%d", a, inv, modulus));
    }
}