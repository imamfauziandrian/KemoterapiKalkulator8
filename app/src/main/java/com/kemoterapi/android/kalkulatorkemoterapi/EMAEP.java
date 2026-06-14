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
import java.lang.Math;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.kemoterapi.android.kalkulatorkemoterapi.widgets.SummaryCardView;

public class EMAEP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emaep);

        PatientInputCache.bind(
                findViewById(R.id.usia),
                findViewById(R.id.beratBadan),
                findViewById(R.id.tinggiBadan),
                findViewById(R.id.serumKreatinin));

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

    }

    /**
     * Fungsi tombol hitung
     */

    public void klikHitung (View view) {

        //mengubah input usia ke variable finalUsiaPasien
        EditText usia = (EditText) findViewById(R.id.usia);
        int usiaPasien = Integer.parseInt(usia.getText().toString());

        //mengubah input berat badan ke variable finalBeratBadan
        EditText berat = (EditText) findViewById(R.id.beratBadan);
        double beratBadan = Double.parseDouble(berat.getText().toString());

        //mengubah input tinggi badan ke variable finalTinggiBadan
        EditText tinggi = (EditText) findViewById(R.id.tinggiBadan);
        double tinggiBadan = Double.parseDouble(tinggi.getText().toString());

        //mengubah input serum Kreatinin ke variable finalSerumKreatinin
        EditText kadarSK = (EditText) findViewById(R.id.serumKreatinin);
        double serumKreatinin = Double.parseDouble(kadarSK.getText().toString());

        //menghitung IMT
        double IMT = hitungIMT(beratBadan, tinggiBadan);
        double isiIMTbulatFinal = pembulatanDuaDesimal(IMT);

        //menampilkan IMT
        TextView viewIMT = (TextView) findViewById(R.id.IndeksMassaTubuh);
        viewIMT.setText(buildSquaredUnitText(isiIMTbulatFinal, " kg/m2"));
        SummaryCardView summaryCard = findViewById(R.id.summaryCard);
        boolean isGfrObese = IMT >= 30;
        summaryCard.setGfrHighlight(isGfrObese);

        //Hitung LPT
        double LPT = hitungLPT(beratBadan, tinggiBadan);
        double luasPermukaanTubuhBulatFinal = pembulatanDuaDesimal(LPT);

        //menampilkan Luas Permukaan Tubuh
        TextView viewLPT = (TextView) findViewById(R.id.LuasPermukaanTubuh);
        viewLPT.setText(buildSquaredUnitText(luasPermukaanTubuhBulatFinal, " m2"));

        //Hitung GFR
        double GFR = hitungGFR(usiaPasien, beratBadan, serumKreatinin);
        double GFRBulatFinal = pembulatanDuaDesimal(GFR);

        //menampilkan GFR
        TextView viewGFR = (TextView) findViewById(R.id.GFR_Normal);
        viewGFR.setText(buildMetricText(GFRBulatFinal, " mL/min"));

        //Hitung GFR Obese
        double GFRobese = hitungGFRobese(usiaPasien, beratBadan, tinggiBadan, serumKreatinin);
        double GFRObeseBulatFinal = pembulatanDuaDesimal(GFRobese);

        double selectedGfr = GfrUtils.getSelectedGfr(GFR, GFRobese, isGfrObese);

        //menampilkan GFR Obese
        TextView viewGFRobese = (TextView) findViewById(R.id.GFR_Obese);
        viewGFRobese.setText(buildMetricText(GFRObeseBulatFinal, " mL/min"));


        //menghitung dosis Etoposide = 100 mg/m2
        double dosisEtoposide100 = LPT * 100;

        //menampilkan kadar Etoposide
        TextView kadarEtoposideHari1 = (TextView) findViewById(R.id.etoposideHari1);
        setAdjustedEtoposideDose(kadarEtoposideHari1, dosisEtoposide100, selectedGfr);
        TextView kadarEtoposideHari2 = (TextView) findViewById(R.id.etoposideHari2);
        setAdjustedEtoposideDose(kadarEtoposideHari2, dosisEtoposide100, selectedGfr);
        TextView kadarEtoposideHari8 = (TextView) findViewById(R.id.etoposideHari8);
        setAdjustedEtoposideDose(kadarEtoposideHari8, dosisEtoposide100, selectedGfr);

        //menghitung dosis Cisplatin = 60 mg/m2
        double dosisCisplatin60 = LPT * 60;

        //menampilkan kadar Cisplatin
        TextView kadarCisplatin60 = (TextView) findViewById(R.id.cisplatin60);
        setAdjustedCisplatinDose(kadarCisplatin60, dosisCisplatin60, selectedGfr);

        //menghitung dosis Mtx IM = 100 mg/m2
        double dosisMtxIM = LPT * 100;

        //menampilkan dosis Mtx Ld
        TextView kadarMtxIM = (TextView) findViewById(R.id.mtxIM);
        setAdjustedMethotrexateDose(kadarMtxIM, dosisMtxIM, selectedGfr);

        //menghitung dosis Mtx IV = 200 mg/m2
        double dosisMtxIV = LPT * 200;

        //menampilkan dosis Mtx IV
        TextView kadarMtxIV = (TextView) findViewById(R.id.mtxIV);
        setAdjustedMethotrexateDose(kadarMtxIV, dosisMtxIV, selectedGfr);

        //dosis Leucovorin fix 15 mg
        //dosis Dactinomycin fix 0,5 mg


    }

    /**
     * Fungsi tombol reset
     */

    public void klikReset (View view) {

        PatientInputCache.clear();

        //mengubah input usia ke variable finalUsiaPasien
        EditText usia = (EditText) findViewById(R.id.usia);
        usia.setText(null);

        //mengubah input berat badan ke variable finalBeratBadan
        EditText berat = (EditText) findViewById(R.id.beratBadan);
        berat.setText(null);

        //mengubah input tinggi badan ke variable finalTinggiBadan
        EditText tinggi = (EditText) findViewById(R.id.tinggiBadan);
        tinggi.setText(null);

        //mengubah input serum Kreatinin ke variable finalSerumKreatinin
        EditText kadarSK = (EditText) findViewById(R.id.serumKreatinin);
        kadarSK.setText(null);

        SummaryCardView summaryCard = findViewById(R.id.summaryCard);
        summaryCard.clearGfrHighlight();

    }

    private void setAdjustedMethotrexateDose(TextView view, double dose, double gfr) {
        setAdjustedDose(view, dose, hitungPengaliDosisMethotrexateBerdasarkanGfr(gfr));
    }

    private void setAdjustedEtoposideDose(TextView view, double dose, double gfr) {
        setAdjustedDose(view, dose, hitungPengaliDosisEtoposideBerdasarkanGfr(gfr));
    }

    private void setAdjustedCisplatinDose(TextView view, double dose, double gfr) {
        double multiplier = hitungPengaliDosisCisplatinBerdasarkanGfr(gfr);
        if (multiplier == 0) {
            view.setText("Ganti carboplatin");
            return;
        }

        setAdjustedDose(view, dose, multiplier);
    }

    private void setAdjustedDose(TextView view, double dose, double multiplier) {
        int originalDose = (int) dose;

        if (multiplier >= 1) {
            view.setText(buildDoseText(originalDose, "mg"));
            return;
        }

        int adjustedDose = (int) Math.round(dose * multiplier);
        SpannableStringBuilder builder = new SpannableStringBuilder();

        String originalText = originalDose + " mg";
        builder.append(originalText);
        builder.setSpan(new StrikethroughSpan(), 0, originalText.length(), 0);
        builder.setSpan(new RelativeSizeSpan(0.75f), 0, originalText.length(), 0);
        applyUnitSpan(builder, originalText);

        builder.append("  ");

        String adjustedText = adjustedDose + " mg";
        int adjustedStart = builder.length();
        builder.append(adjustedText);
        builder.setSpan(new StyleSpan(Typeface.BOLD), adjustedStart, builder.length(), 0);
        applyUnitSpan(builder, adjustedText, adjustedStart);

        view.setText(builder);
    }

    static double hitungPengaliDosisMethotrexateBerdasarkanGfr(double gfr) {
        if (gfr >= 30 && gfr < 60) {
            return 0.75;
        }

        if (gfr < 30) {
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


    //Hitung LPT
    //LPT = akar kuadrat dari ((BB x TB)/3600)
    static double hitungLPT(double beratBadan, double tinggiBadan) {
        double LPT = Math.sqrt((beratBadan * tinggiBadan) / 3600);
        return LPT;

    }

    //Hitung GFR
    //GFR = ((140-Umur) x BeratBadan x 0.85) / (72 x SK)
    static double hitungGFR(double umur, double beratBadan, double serumKreatinin) {
        double GFR = ((140 - umur) * beratBadan * 0.85) / (72 * serumKreatinin);
        return GFR;
    }

    //Hitung GFR Obese
    //GFR Obese = ((146-Umur) x BeratBadan x 0.287) + (9.74 * TB * TB) / (60 x SK)
    static double hitungGFRobese(double umur, double beratBadan, double tinggiBadan, double serumKreatinin) {
        double GFRobese = ((146 - umur) * ((beratBadan * 0.287) + (((tinggiBadan / 100) * (tinggiBadan / 100)) * 9.74))) / (60 * serumKreatinin);
        return GFRobese;
    }

    //menghitung IMT
    //IMT = BB / ((TB/100)x(TB/100))
    static double hitungIMT(double beratBadan, double tinggiBadan) {
        double IMT = beratBadan / ((tinggiBadan / 100) * (tinggiBadan / 100));
        return IMT;
    }

    //pembulatan ke 2 desimal
    public static double pembulatanDuaDesimal(double nilai) {
        double pembulatan1 = Math.round(nilai * 100);
        double pembulatan2 = pembulatan1 / 100;
        return pembulatan2;
    }

}
