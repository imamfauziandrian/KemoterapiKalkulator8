package com.kemoterapi.android.kalkulatorkemoterapi;

import android.content.Context;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.harinugroho.android.kemoterapikalkulator", appContext.getPackageName());
    }

    @Test
    public void buildDoseText_makesUnitSmallerThanValue() {
        Spanned text = (Spanned) PaclitaxelCarboplatin.buildDoseText(315, "mg");

        assertEquals("315 mg", text.toString());

        RelativeSizeSpan[] spans = text.getSpans(0, text.length(), RelativeSizeSpan.class);
        assertEquals(1, spans.length);
        assertEquals(4, text.getSpanStart(spans[0]));
        assertEquals(6, text.getSpanEnd(spans[0]));
        assertEquals(0.72f, spans[0].getSizeChange(), 0.0f);
    }

    @Test
    public void buildSquaredUnitText_usesSuperscriptForSquareUnit() {
        Spanned text = (Spanned) PaclitaxelCarboplatin.buildSquaredUnitText(22.45, " kg/m2");

        assertEquals("22.45 kg/m2", text.toString());

        SuperscriptSpan[] superscriptSpans = text.getSpans(0, text.length(), SuperscriptSpan.class);
        assertEquals(1, superscriptSpans.length);
        assertEquals(10, text.getSpanStart(superscriptSpans[0]));
        assertEquals(11, text.getSpanEnd(superscriptSpans[0]));

        RelativeSizeSpan[] sizeSpans = text.getSpans(0, text.length(), RelativeSizeSpan.class);
        assertEquals(2, sizeSpans.length);

        RelativeSizeSpan unitSpan = null;
        RelativeSizeSpan superscriptSizeSpan = null;
        for (RelativeSizeSpan span : sizeSpans) {
            if (text.getSpanStart(span) == 5 && text.getSpanEnd(span) == 11) {
                unitSpan = span;
            }
            if (text.getSpanStart(span) == 10 && text.getSpanEnd(span) == 11) {
                superscriptSizeSpan = span;
            }
        }

        assertNotNull(unitSpan);
        assertEquals(0.72f, unitSpan.getSizeChange(), 0.0f);
        assertNotNull(superscriptSizeSpan);
        assertEquals(0.75f, superscriptSizeSpan.getSizeChange(), 0.0f);
    }

    @Test
    public void buildMetricText_makesUnitSmallerThanMetricValue() {
        Spanned text = (Spanned) PaclitaxelCarboplatin.buildMetricText(88.45, " mL/min");

        assertEquals("88.45 mL/min", text.toString());

        RelativeSizeSpan[] spans = text.getSpans(0, text.length(), RelativeSizeSpan.class);
        assertEquals(1, spans.length);
        assertEquals(5, text.getSpanStart(spans[0]));
        assertEquals(12, text.getSpanEnd(spans[0]));
        assertEquals(0.72f, spans[0].getSizeChange(), 0.0f);
    }
}
