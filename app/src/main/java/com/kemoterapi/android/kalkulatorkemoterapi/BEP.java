package com.kemoterapi.android.kalkulatorkemoterapi;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

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

        double lpt = hitungLPT(beratBadan, tinggiBadan);
        TextView viewLPT = findViewById(R.id.LuasPermukaanTubuh);
        viewLPT.setText(buildSquaredUnitText(pembulatanDuaDesimal(lpt), " m2"));

        double gfr = hitungGFR(usiaPasien, beratBadan, serumKreatinin);
        TextView viewGFR = findViewById(R.id.GFR_Normal);
        viewGFR.setText(buildMetricText(pembulatanDuaDesimal(gfr), " mL/min"));

        double gfrObese = hitungGFRobese(usiaPasien, beratBadan, tinggiBadan, serumKreatinin);
        TextView viewGFRObese = findViewById(R.id.GFR_Obese);
        viewGFRObese.setText(buildMetricText(pembulatanDuaDesimal(gfrObese), " mL/min"));

        TextView kadarEtoposide = findViewById(R.id.etoposide);
        kadarEtoposide.setText(buildDoseText((int) (lpt * 100), "mg"));

        TextView kadarCisplatin = findViewById(R.id.cisplatin);
        kadarCisplatin.setText(buildDoseText((int) (lpt * 20), "mg"));
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
    }

    private static CharSequence buildDoseText(int dose, String unit) {
        SpannableStringBuilder builder = new SpannableStringBuilder(String.valueOf(dose));
        int unitStart = builder.length();
        builder.append(" ").append(unit);
        builder.setSpan(new RelativeSizeSpan(0.72f), unitStart, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
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

    public static double pembulatanDuaDesimal(double nilai) {
        double pembulatan1 = Math.round(nilai * 100);
        return pembulatan1 / 100;
    }
}
