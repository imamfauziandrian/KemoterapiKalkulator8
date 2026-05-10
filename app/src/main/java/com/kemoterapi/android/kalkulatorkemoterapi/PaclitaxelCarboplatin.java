package com.kemoterapi.android.kalkulatorkemoterapi;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.textfield.TextInputLayout;
import com.kemoterapi.android.kalkulatorkemoterapi.ui.info.AucInfoActivity;

public class PaclitaxelCarboplatin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paclitaxel_carboplatin);

        PatientInputCache.bind(
                findViewById(R.id.usia),
                findViewById(R.id.beratBadan),
                findViewById(R.id.tinggiBadan),
                findViewById(R.id.serumKreatinin));

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        AutoCompleteTextView aucOption = findViewById(R.id.aucOption);
        ArrayAdapter<String> aucAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                new String[]{"4", "5", "6"});
        aucOption.setAdapter(aucAdapter);
        aucOption.setText("6", false);

        AutoCompleteTextView bevacizumabOption = findViewById(R.id.bevacizumabOption);
        CharSequence[] bevOptions = new CharSequence[]{
                "Tidak",
                buildBevacizumabOption("7,5"),
                buildBevacizumabOption("15")
        };
        ArrayAdapter<CharSequence> bevAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                bevOptions);
        bevacizumabOption.setAdapter(bevAdapter);
        bevacizumabOption.setOnItemClickListener((parent, view, position, id) -> {
                CharSequence selected = (CharSequence) parent.getItemAtPosition(position);
                bevacizumabOption.setText(selected, false);
                updateBevacizumabCard(selected.toString().trim(), getCurrentBeratBadan());
        });
        bevacizumabOption.setText("Tidak", false);
        updateBevacizumabCard("Tidak", 0);
    }

    /**
     * Fungsi tombol hitung
     */
    public void klikHitung(View view) {
        TextInputLayout tilUsia = findViewById(R.id.tilUsia);
        TextInputLayout tilBerat = findViewById(R.id.tilBerat);
        TextInputLayout tilTinggi = findViewById(R.id.tilTinggi);
        TextInputLayout tilKreatinin = findViewById(R.id.tilKreatinin);
        TextInputLayout tilAuc = findViewById(R.id.tilAuc);
        TextInputLayout tilBevacizumab = findViewById(R.id.tilBevacizumab);
        TextView errorMessage = findViewById(R.id.errorMessage);

        errorMessage.setVisibility(View.GONE);
        errorMessage.setText("");

        //mengubah input usia ke variable finalUsiaPasien
        EditText usia = findViewById(R.id.usia);
        if (TextUtils.isEmpty(usia.getText())) {
            errorMessage.setText("Usia wajib diisi");
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }
        int usiaPasien = Integer.parseInt(usia.getText().toString());

        //mengubah input berat badan ke variable finalBeratBadan
        EditText berat = findViewById(R.id.beratBadan);
        if (TextUtils.isEmpty(berat.getText())) {
            errorMessage.setText("Berat wajib diisi");
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }
        double beratBadan = Double.parseDouble(berat.getText().toString());

        //mengubah input tinggi badan ke variable finalTinggiBadan
        EditText tinggi = findViewById(R.id.tinggiBadan);
        if (TextUtils.isEmpty(tinggi.getText())) {
            errorMessage.setText("Tinggi wajib diisi");
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }
        double tinggiBadan = Double.parseDouble(tinggi.getText().toString());

        //mengubah input serum Kreatinin ke variable finalSerumKreatinin
        EditText kadarSK = findViewById(R.id.serumKreatinin);
        if (TextUtils.isEmpty(kadarSK.getText())) {
            errorMessage.setText("Kreatinin wajib diisi");
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }
        double serumKreatinin = Double.parseDouble(kadarSK.getText().toString());

        AutoCompleteTextView aucOption = findViewById(R.id.aucOption);
        if (TextUtils.isEmpty(aucOption.getText())) {
            errorMessage.setText("AUC wajib diisi");
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }
        double auc = getSelectedAuc(aucOption);

        AutoCompleteTextView bevacizumabOption = findViewById(R.id.bevacizumabOption);
        if (TextUtils.isEmpty(bevacizumabOption.getText())) {
            errorMessage.setText("Bevacizumab wajib diisi");
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }
        String bevacizumabSelection = bevacizumabOption.getText().toString().trim();

        //menghitung IMT
        double IMT = hitungIMT(beratBadan, tinggiBadan);
        double isiIMTbulatFinal = pembulatanDuaDesimal(IMT);

        //menampilkan IMT
        TextView viewIMT = findViewById(R.id.IndeksMassaTubuh);
        viewIMT.setText(isiIMTbulatFinal + " kg/m2");

        //Hitung LPT
        double LPT = hitungLPT(beratBadan, tinggiBadan);
        double luasPermukaanTubuhBulatFinal = pembulatanDuaDesimal(LPT);

        //menampilkan Luas Permukaan Tubuh
        TextView viewLPT = findViewById(R.id.LuasPermukaanTubuh);
        viewLPT.setText(luasPermukaanTubuhBulatFinal + " m2");

        //Hitung GFR
        double GFR = hitungGFR(usiaPasien, beratBadan, serumKreatinin);

        //menampilkan GFR
        TextView viewGFR = findViewById(R.id.GFR_Normal);
        viewGFR.setText(pembulatanDuaDesimal(GFR) + " mL/min");

        //Hitung GFR Obese
        double GFRobese = hitungGFRobese(usiaPasien, beratBadan, tinggiBadan, serumKreatinin);

        //menampilkan GFR Obese
        TextView viewGFRobese = findViewById(R.id.GFR_Obese);
        viewGFRobese.setText(pembulatanDuaDesimal(GFRobese) + " mL/min");

        //menghitung dosis Paclitaxel = LPT x 175 mg
        double dosisPaclitaxel = LPT * 175;

        //menampilkan kadar Paclitaxel
        TextView kadarPaclitaxel = findViewById(R.id.paclitaxel);
        kadarPaclitaxel.setText((int) dosisPaclitaxel + " mg");

        //menghitung dosis Carboplatin = (GFR + 25) x AUC
        double dosisCarboplatin = (GFR + 25) * auc;

        //menampilkan kadar Carboplatin Normal
        TextView kadarCarboplatin = findViewById(R.id.carboplatin);
        setCarboplatinDose(kadarCarboplatin, dosisCarboplatin, hitungDosisMaksimumCarboplatin(auc));

        //menghitung dosis Carboplatin Obese = (GFR Obese + 25) x AUC
        double dosisCarboplatinObese = (GFRobese + 25) * auc;

        //menampilkan kadar Carboplatin Obese
        TextView kadarCarboplatinObese = findViewById(R.id.carboplatinObese);
        setCarboplatinDose(kadarCarboplatinObese, dosisCarboplatinObese, hitungDosisMaksimumCarboplatin(auc));

        //menghitung dosis Carboplatin GFR 40-60 = 250 x LPT
        double dosisCarboplatinMildAki = 250 * LPT;

        //menampilkan kadar Carboplatin Obese
        TextView kadarCarboplatinMildAki = findViewById(R.id.carboplatin4060);
        kadarCarboplatinMildAki.setText((int) dosisCarboplatinMildAki + " mg");

        //menghitung dosis Carboplatin GFR 40 = 200 x LPT
        double dosisCarboplatinSevereAki = 200 * LPT;

        //menampilkan kadar Carboplatin Obese
        TextView kadarCarboplatinSevereAki = findViewById(R.id.carboplatin40);
        kadarCarboplatinSevereAki.setText((int) dosisCarboplatinSevereAki + " mg");

        updateBevacizumabCard(bevacizumabSelection, beratBadan);

        highlightPaclitaxelCard(dosisPaclitaxel > 0);
        highlightCarboplatinCards(GFR, GFRobese);
    }

    /**
     * Fungsi tombol reset
     */
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

        AutoCompleteTextView aucOption = findViewById(R.id.aucOption);
        aucOption.setText("6", false);

        AutoCompleteTextView bevacizumabOption = findViewById(R.id.bevacizumabOption);
        bevacizumabOption.setText("Tidak", false);

        TextView errorMessage = findViewById(R.id.errorMessage);
        errorMessage.setVisibility(View.GONE);
        errorMessage.setText("");

        TextView paclitaxel = findViewById(R.id.paclitaxel);
        paclitaxel.setText("0");

        TextView carboplatin = findViewById(R.id.carboplatin);
        carboplatin.setText("0");

        TextView carboplatinObese = findViewById(R.id.carboplatinObese);
        carboplatinObese.setText("0");

        TextView carboplatin4060 = findViewById(R.id.carboplatin4060);
        carboplatin4060.setText("0");

        TextView carboplatin40 = findViewById(R.id.carboplatin40);
        carboplatin40.setText("0");

        MaterialCardView cardBevacizumab = findViewById(R.id.cardBevacizumab);
        TextView bevacizumabSubtitle = findViewById(R.id.bevacizumabSubtitle);
        TextView bevacizumabDose = findViewById(R.id.bevacizumabDose);
        cardBevacizumab.setVisibility(View.GONE);
        bevacizumabSubtitle.setText("Dosis berdasarkan BB");
        bevacizumabDose.setText("0");
        applyHighlight(cardBevacizumab, false);

        highlightPaclitaxelCard(false);
        highlightCarboplatinCards(0, 0);
    }

    public void klikInfoAuc(View view) {
        Intent intent = new Intent(this, AucInfoActivity.class);
        startActivity(intent);
    }

    private void clearErrors(TextInputLayout... layouts) {
        for (TextInputLayout layout : layouts) {
            layout.setError(null);
        }
    }

    private double getSelectedAuc(AutoCompleteTextView aucOption) {
        String value = aucOption.getText() != null ? aucOption.getText().toString().trim() : "";
        if (value.isEmpty()) {
            return 6;
        }
        return Double.parseDouble(value);
    }

    private CharSequence buildBevacizumabOption(String dose) {
        SpannableStringBuilder builder = new SpannableStringBuilder(dose);
        int unitStart = builder.length();
        builder.append(" mg/kgBB");
        builder.setSpan(new RelativeSizeSpan(0.72f), unitStart, builder.length(), 0);
        return builder;
    }

    private double getCurrentBeratBadan() {
        EditText berat = findViewById(R.id.beratBadan);
        if (berat.getText() == null) {
            return 0;
        }

        String value = berat.getText().toString().trim();
        if (value.isEmpty()) {
            return 0;
        }

        return Double.parseDouble(value);
    }

    private void updateBevacizumabCard(String selection, double beratBadan) {
        MaterialCardView cardBevacizumab = findViewById(R.id.cardBevacizumab);
        TextView bevacizumabSubtitle = findViewById(R.id.bevacizumabSubtitle);
        TextView bevacizumabDose = findViewById(R.id.bevacizumabDose);

        if (selection.isEmpty() || "Tidak".equalsIgnoreCase(selection)) {
            cardBevacizumab.setVisibility(View.GONE);
            bevacizumabSubtitle.setText("Dosis berdasarkan BB");
            bevacizumabDose.setText("0");
            applyHighlight(cardBevacizumab, false);
            return;
        }

        double dosisPerKg = selection.startsWith("15") ? 15 : 7.5;
        int totalDose = (int) Math.round(beratBadan * dosisPerKg);

        cardBevacizumab.setVisibility(View.VISIBLE);
        bevacizumabSubtitle.setText("Dosis berdasarkan BB x " + selection);
        bevacizumabDose.setText(totalDose + " mg");
        applyHighlight(cardBevacizumab, true);
    }

    static double hitungDosisMaksimumCarboplatin(double auc) {
        return auc * 150;
    }

    private void setCarboplatinDose(TextView view, double actualDose, double maxDose) {
        int actual = (int) Math.round(actualDose);
        int max = (int) Math.round(maxDose);

        if (actualDose > maxDose) {
            SpannableStringBuilder builder = new SpannableStringBuilder();
            String actualText = actual + " mg";
            builder.append(actualText);
            builder.setSpan(new StrikethroughSpan(), 0, actualText.length(), 0);
            builder.setSpan(new RelativeSizeSpan(0.75f), 0, actualText.length(), 0);

            builder.append("\n");

            String maxText = max + " mg";
            int start = builder.length();
            builder.append(maxText);
            builder.setSpan(new StyleSpan(Typeface.BOLD), start, builder.length(), 0);

            view.setText(builder);
            return;
        }

        view.setText(actual + " mg");
    }

    private void highlightPaclitaxelCard(boolean active) {
        MaterialCardView card = findViewById(R.id.cardPaclitaxel);
        applyHighlight(card, active);
    }

    private void highlightCarboplatinCards(double gfr, double gfrObese) {
        applyHighlight(findViewById(R.id.cardCarboplatinNormal), gfr >= 60);
        applyHighlight(findViewById(R.id.cardCarboplatinObese), gfrObese >= 60);
        applyHighlight(findViewById(R.id.cardCarboplatin4060), gfr >= 40 && gfr < 60);
        applyHighlight(findViewById(R.id.cardCarboplatin40), gfr < 40);
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
