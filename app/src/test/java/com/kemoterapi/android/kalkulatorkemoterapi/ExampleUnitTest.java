package com.kemoterapi.android.kalkulatorkemoterapi;

import org.junit.Test;

import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;

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

    @Test
    public void methotrexateGfrDoseMultiplier_usesRenalAdjustmentRules() {
        assertEquals(1.0, Methotrexate.hitungPengaliDosisBerdasarkanGfr(60), 0.0);
        assertEquals(0.75, Methotrexate.hitungPengaliDosisBerdasarkanGfr(59), 0.0);
        assertEquals(0.75, Methotrexate.hitungPengaliDosisBerdasarkanGfr(30), 0.0);
        assertEquals(0.5, Methotrexate.hitungPengaliDosisBerdasarkanGfr(29), 0.0);
    }

    @Test
    public void methotrexateSquaredUnitText_formatsUnitAndSuperscript() {
        CharSequence text = Methotrexate.buildSquaredUnitText(21.5, " kg/m2");

        assertEquals("21.5 kg/m2", text.toString());
        assertTrue(text instanceof Spanned);

        Spanned spanned = (Spanned) text;
        assertEquals(1, spanned.getSpans(9, 10, SuperscriptSpan.class).length);
        assertTrue(spanned.getSpans(4, text.length(), RelativeSizeSpan.class).length >= 1);
    }
}
