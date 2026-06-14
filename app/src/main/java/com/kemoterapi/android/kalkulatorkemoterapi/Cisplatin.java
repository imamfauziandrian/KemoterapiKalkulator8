package com.kemoterapi.android.kalkulatorkemoterapi;

import android.content.Intent;
import android.os.Bundle;
import android.content.res.ColorStateList;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SuperscriptSpan;
import android.graphics.Typeface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.color.MaterialColors;
import com.kemoterapi.android.kalkulatorkemoterapi.ui.info.CisplatinInfoActivity;
import com.kemoterapi.android.kalkulatorkemoterapi.widgets.SummaryCardView;

public class Cisplatin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cisplatin);

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

        TextView errorMessage = findViewById(R.id.errorMessage);
        errorMessage.setVisibility(View.GONE);
        errorMessage.setText("");

        //mengubah input usia ke variable finalUsiaPasien
        EditText usia = (EditText) findViewById(R.id.usia);
        if (TextUtils.isEmpty(usia.getText())) {
            errorMessage.setText("Usia wajib diisi");
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }
        int usiaPasien = Integer.parseInt(usia.getText().toString());

        //mengubah input berat badan ke variable finalBeratBadan
        EditText berat = (EditText) findViewById(R.id.beratBadan);
        if (TextUtils.isEmpty(berat.getText())) {
            errorMessage.setText("Berat wajib diisi");
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }
        double beratBadan = Double.parseDouble(berat.getText().toString());

        //mengubah input tinggi badan ke variable finalTinggiBadan
        EditText tinggi = (EditText) findViewById(R.id.tinggiBadan);
        if (TextUtils.isEmpty(tinggi.getText())) {
            errorMessage.setText("Tinggi wajib diisi");
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }
        double tinggiBadan = Double.parseDouble(tinggi.getText().toString());

        //mengubah input serum Kreatinin ke variable finalSerumKreatinin
        EditText kadarSK = (EditText) findViewById(R.id.serumKreatinin);
        if (TextUtils.isEmpty(kadarSK.getText())) {
            errorMessage.setText("Kreatinin wajib diisi");
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }
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


        //menghitung dosis Ciplatin 75 mg/m2
        double dosisCisplatin75 = LPT * 75;

        //menampilkan dosis Cisplatin 75
        TextView textDosisCisplatin75 = (TextView) findViewById(R.id.cisplatin75);
        setAdjustedCisplatinDose(textDosisCisplatin75, dosisCisplatin75, selectedGfr);

        //menghitung dosis Ciplatin 50 mg/m2
        double dosisCisplatin50 = LPT * 50;

        //menampilkan dosis Cisplatin 50
        TextView textDosisCisplatin50 = (TextView) findViewById(R.id.cisplatin50);
        setAdjustedCisplatinDose(textDosisCisplatin50, dosisCisplatin50, selectedGfr);

        //menghitung dosis Ciplatin 40 mg/m2
        double dosisCisplatin40 = LPT * 40;

        //menampilkan dosis Cisplatin 40
        TextView textDosisCisplatin40 = (TextView) findViewById(R.id.cisplatin40);
        setAdjustedCisplatinDose(textDosisCisplatin40, dosisCisplatin40, selectedGfr);

        updateGfrWarning(selectedGfr);
        highlightCisplatinCards(selectedGfr);

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

        TextView errorMessage = findViewById(R.id.errorMessage);
        errorMessage.setVisibility(View.GONE);
        errorMessage.setText("");

        TextView viewIMT = findViewById(R.id.IndeksMassaTubuh);
        viewIMT.setText("0");

        TextView viewLPT = findViewById(R.id.LuasPermukaanTubuh);
        viewLPT.setText("0");

        TextView viewGFR = findViewById(R.id.GFR_Normal);
        viewGFR.setText("0");

        TextView viewGFRobese = findViewById(R.id.GFR_Obese);
        viewGFRobese.setText("0");

        TextView cisplatin75 = findViewById(R.id.cisplatin75);
        cisplatin75.setText("0");

        TextView cisplatin50 = findViewById(R.id.cisplatin50);
        cisplatin50.setText("0");

        TextView cisplatin40 = findViewById(R.id.cisplatin40);
        cisplatin40.setText("0");

        SummaryCardView summaryCard = findViewById(R.id.summaryCard);
        summaryCard.clearGfrHighlight();

        updateGfrWarning(0);
        highlightCisplatinCards(0);

    }

    public void klikInfoCisplatin(View view) {
        Intent intent = new Intent(this, CisplatinInfoActivity.class);
        startActivity(intent);
    }

    static CharSequence buildDoseText(int dose, String unit) {
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

    private void setAdjustedCisplatinDose(TextView view, double dose, double gfr) {
        int originalDose = (int) dose;
        double multiplier = getCisplatinDoseMultiplier(gfr);

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

        builder.append("\n");

        String adjustedText = adjustedDose + " mg";
        int adjustedStart = builder.length();
        builder.append(adjustedText);
        builder.setSpan(new StyleSpan(Typeface.BOLD), adjustedStart, builder.length(), 0);
        applyUnitSpan(builder, adjustedText, adjustedStart);

        view.setText(builder);
    }

    private double getCisplatinDoseMultiplier(double gfr) {
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

    private void updateGfrWarning(double gfr) {
        MaterialCardView warningCard = findViewById(R.id.cardGfrWarning);
        warningCard.setVisibility(gfr > 0 && gfr < 40 ? View.VISIBLE : View.GONE);
    }

    private void highlightCisplatinCards(double gfr) {
        boolean allowed = gfr >= 40;
        applyHighlight(findViewById(R.id.cardCisplatin75), allowed);
        applyHighlight(findViewById(R.id.cardCisplatin50), allowed);
        applyHighlight(findViewById(R.id.cardCisplatin40), allowed);
    }

    private void applyHighlight(MaterialCardView card, boolean active) {
        int strokeWidth = active ? dpToPx(2) : dpToPx(1);
        int strokeColor = MaterialColors.getColor(card,
                active ? com.google.android.material.R.attr.colorPrimary
                        : com.google.android.material.R.attr.colorOutline);
        int backgroundColor = MaterialColors.getColor(card,
                active ? com.google.android.material.R.attr.colorPrimaryContainer
                        : com.google.android.material.R.attr.colorSurfaceContainerHighest);

        card.setStrokeWidth(strokeWidth);
        card.setStrokeColor(ColorStateList.valueOf(strokeColor));
        card.setCardBackgroundColor(backgroundColor);
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
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
