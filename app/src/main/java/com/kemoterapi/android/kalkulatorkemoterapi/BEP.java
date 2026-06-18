package com.kemoterapi.android.kalkulatorkemoterapi;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.kemoterapi.android.kalkulatorkemoterapi.widgets.SummaryCardView;

public class BEP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bep);

        PatientInputCache.bind(
                findViewById(R.id.usia),
                findViewById(R.id.beratBadan),
                findViewById(R.id.tinggiBadan),
                findViewById(R.id.serumKreatinin));

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        TextView kadarBleomycin = findViewById(R.id.bleomycin);
        kadarBleomycin.setText(buildDoseText(30, "units"));
    }

    public void klikHitung(View view) {
        EditText usia = findViewById(R.id.usia);
        int usiaPasien = Integer.parseInt(usia.getText().toString());

        EditText berat = findViewById(R.id.beratBadan);
        double beratBadan = Double.parseDouble(berat.getText().toString());

        EditText tinggi = findViewById(R.id.tinggiBadan);
        double tinggiBadan = Double.parseDouble(tinggi.getText().toString());

        EditText kadarSK = findViewById(R.id.serumKreatinin);
        double serumKreatinin = Double.parseDouble(kadarSK.getText().toString());

        double imt = hitungIMT(beratBadan, tinggiBadan);
        TextView viewIMT = findViewById(R.id.IndeksMassaTubuh);
        viewIMT.setText(buildSquaredUnitText(pembulatanDuaDesimal(imt), " kg/m2"));
        SummaryCardView summaryCard = findViewById(R.id.summaryCard);
        boolean isGfrObese = imt >= 30;
        summaryCard.setGfrHighlight(isGfrObese);

        double lpt = hitungLPT(beratBadan, tinggiBadan);
        TextView viewLPT = findViewById(R.id.LuasPermukaanTubuh);
        viewLPT.setText(buildSquaredUnitText(pembulatanDuaDesimal(lpt), " m2"));

        double gfr = hitungGFR(usiaPasien, beratBadan, serumKreatinin);
        TextView viewGFR = findViewById(R.id.GFR_Normal);
        viewGFR.setText(buildMetricText(pembulatanDuaDesimal(gfr), " mL/min"));

        double gfrObese = hitungGFRobese(usiaPasien, beratBadan, tinggiBadan, serumKreatinin);
        TextView viewGFRObese = findViewById(R.id.GFR_Obese);
        viewGFRObese.setText(buildMetricText(pembulatanDuaDesimal(gfrObese), " mL/min"));

        double selectedGfr = GfrUtils.getSelectedGfr(gfr, gfrObese, isGfrObese);

        TextView kadarBleomycin = findViewById(R.id.bleomycin);
        setAdjustedDose(
                kadarBleomycin,
                30,
                "units",
                hitungPengaliDosisBleomycinBerdasarkanGfr(selectedGfr));

        TextView kadarEtoposide = findViewById(R.id.etoposide);
        setAdjustedDose(
                kadarEtoposide,
                lpt * 100,
                "mg",
                hitungPengaliDosisEtoposideBerdasarkanGfr(selectedGfr));

        TextView kadarCisplatin = findViewById(R.id.cisplatin);
        setAdjustedCisplatinDose(kadarCisplatin, lpt * 20, selectedGfr);
    }

    public void klikReset(View view) {
        PatientInputCache.clear();

        EditText usia = findViewById(R.id.usia);
        usia.setText(null);

        EditText berat = findViewById(R.id.beratBadan);
        berat.setText(null);

        EditText tinggi = findViewById(R.id.tinggiBadan);
        tinggi.setText(null);

        EditText kadarSK = findViewById(R.id.serumKreatinin);
        kadarSK.setText(null);

        ((TextView) findViewById(R.id.IndeksMassaTubuh)).setText("0");
        ((TextView) findViewById(R.id.LuasPermukaanTubuh)).setText("0");
        ((TextView) findViewById(R.id.GFR_Normal)).setText("0");
        ((TextView) findViewById(R.id.GFR_Obese)).setText("0");
        ((TextView) findViewById(R.id.bleomycin)).setText(buildDoseText(30, "units"));
        ((TextView) findViewById(R.id.etoposide)).setText("0");
        ((TextView) findViewById(R.id.cisplatin)).setText("0");

        SummaryCardView summaryCard = findViewById(R.id.summaryCard);
        summaryCard.clearGfrHighlight();
    }

    private void setAdjustedCisplatinDose(TextView view, double dose, double gfr) {
        double multiplier = hitungPengaliDosisCisplatinBerdasarkanGfr(gfr);
        if (multiplier == 0) {
            view.setText("Ganti carboplatin");
            return;
        }

        setAdjustedDose(view, dose, "mg", multiplier);
    }

    private void setAdjustedDose(TextView view, double dose, String unit, double multiplier) {
        int originalDose = (int) dose;

        if (multiplier >= 1) {
            view.setText(buildDoseText(originalDose, unit));
            return;
        }

        int adjustedDose = hitungDosisDisesuaikan(dose, multiplier);
        SpannableStringBuilder builder = new SpannableStringBuilder();

        String originalText = originalDose + " " + unit;
        builder.append(originalText);
        builder.setSpan(new StrikethroughSpan(), 0, originalText.length(), 0);
        builder.setSpan(new RelativeSizeSpan(0.75f), 0, originalText.length(), 0);
        applyUnitSpan(builder, originalText);

        builder.append("  ");

        String adjustedText = adjustedDose + " " + unit;
        int adjustedStart = builder.length();
        builder.append(adjustedText);
        builder.setSpan(new StyleSpan(Typeface.BOLD), adjustedStart, builder.length(), 0);
        applyUnitSpan(builder, adjustedText, adjustedStart);

        view.setText(builder);
    }

    private static CharSequence buildDoseText(int dose, String unit) {
        SpannableStringBuilder builder = new SpannableStringBuilder(String.valueOf(dose));
        int unitStart = builder.length();
        builder.append(" ").append(unit);
        builder.setSpan(new RelativeSizeSpan(0.72f), unitStart, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    private void applyUnitSpan(SpannableStringBuilder builder, String text) {
        applyUnitSpan(builder, text, 0);
    }

    private void applyUnitSpan(SpannableStringBuilder builder, String text, int startOffset) {
        int unitStart = text.indexOf(' ');
        if (unitStart < 0) {
            return;
        }
        builder.setSpan(new RelativeSizeSpan(0.72f), startOffset + unitStart, startOffset + text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    static CharSequence buildSquaredUnitText(double value, String unitText) {
        String text = value + unitText;
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        int unitStart = String.valueOf(value).length();
        builder.setSpan(new RelativeSizeSpan(0.72f), unitStart, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int superscriptIndex = text.lastIndexOf('2');
        if (superscriptIndex >= 0) {
            builder.setSpan(new SuperscriptSpan(), superscriptIndex, superscriptIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new RelativeSizeSpan(0.75f), superscriptIndex, superscriptIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    static CharSequence buildMetricText(double value, String unitText) {
        String text = value + unitText;
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        int unitStart = String.valueOf(value).length();
        builder.setSpan(new RelativeSizeSpan(0.72f), unitStart, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    static double hitungLPT(double beratBadan, double tinggiBadan) {
        return Math.sqrt((beratBadan * tinggiBadan) / 3600);
    }

    static double hitungGFR(double umur, double beratBadan, double serumKreatinin) {
        return ((140 - umur) * beratBadan * 0.85) / (72 * serumKreatinin);
    }

    static double hitungGFRobese(double umur, double beratBadan, double tinggiBadan, double serumKreatinin) {
        return ((146 - umur) * ((beratBadan * 0.287) + (((tinggiBadan / 100) * (tinggiBadan / 100)) * 9.74))) / (60 * serumKreatinin);
    }

    static double hitungIMT(double beratBadan, double tinggiBadan) {
        return beratBadan / ((tinggiBadan / 100) * (tinggiBadan / 100));
    }

    static double hitungPengaliDosisBleomycinBerdasarkanGfr(double gfr) {
        if (gfr >= 10 && gfr < 50) {
            return 0.75;
        }

        if (gfr < 10) {
            return 0.5;
        }

        return 1;
    }

    static double hitungPengaliDosisEtoposideBerdasarkanGfr(double gfr) {
        if (gfr >= 10 && gfr < 50) {
            return 0.75;
        }

        if (gfr < 10) {
            return 0.5;
        }

        return 1;
    }

    static double hitungPengaliDosisCisplatinBerdasarkanGfr(double gfr) {
        if (gfr >= 50 && gfr < 60) {
            return 0.75;
        }

        if (gfr >= 40 && gfr < 50) {
            return 0.5;
        }

        if (gfr < 40) {
            return 0;
        }

        return 1;
    }

    static int hitungDosisDisesuaikan(double dose, double multiplier) {
        return (int) Math.round(dose * multiplier);
    }

    public static double pembulatanDuaDesimal(double nilai) {
        double pembulatan1 = Math.round(nilai * 100);
        return pembulatan1 / 100;
    }
}
