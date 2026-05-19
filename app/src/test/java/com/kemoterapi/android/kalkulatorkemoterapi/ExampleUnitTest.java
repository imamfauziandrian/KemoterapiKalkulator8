package com.kemoterapi.android.kalkulatorkemoterapi;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void carboplatinMaxDose_isAucTimes150() {
        assertEquals(900.0, PaclitaxelCarboplatin.hitungDosisMaksimumCarboplatin(6), 0.0);
        assertEquals(750.0, PaclitaxelCarboplatin.hitungDosisMaksimumCarboplatin(5), 0.0);
        assertEquals(600.0, PaclitaxelCarboplatin.hitungDosisMaksimumCarboplatin(4), 0.0);
    }

    @Test
    public void gemcitabinCarboplatinDose_usesSelectedAuc() {
        assertEquals(500.0, GemcitabinCarboplatin.hitungDosisCarboplatin(100, 4), 0.0);
        assertEquals(625.0, GemcitabinCarboplatin.hitungDosisCarboplatin(100, 5), 0.0);
        assertEquals(750.0, GemcitabinCarboplatin.hitungDosisCarboplatin(100, 6), 0.0);
    }

    @Test
    public void gemcitabinCarboplatinMaxDose_isAucTimes150() {
        assertEquals(600.0, GemcitabinCarboplatin.hitungDosisMaksimumCarboplatin(4), 0.0);
        assertEquals(750.0, GemcitabinCarboplatin.hitungDosisMaksimumCarboplatin(5), 0.0);
        assertEquals(900.0, GemcitabinCarboplatin.hitungDosisMaksimumCarboplatin(6), 0.0);
    }
}
