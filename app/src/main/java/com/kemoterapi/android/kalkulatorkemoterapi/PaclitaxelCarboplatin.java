package com.kemoterapi.android.kalkulatorkemoterapi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.kemoterapi.android.kalkulatorkemoterapi.ui.info.AucInfoActivity;

public class PaclitaxelCarboplatin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paclitaxel_carboplatin);

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
        ArrayAdapter<String> bevAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                new String[]{"No", "Yes"});
        bevacizumabOption.setAdapter(bevAdapter);
        bevacizumabOption.setText("No", false);

        ChipGroup bevacizumabDoseGroup = findViewById(R.id.bevacizumabDoseGroup);
        bevacizumabOption.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            boolean showDoseGroup = "Yes".equalsIgnoreCase(selected);
            bevacizumabDoseGroup.setVisibility(showDoseGroup ? View.VISIBLE : View.GONE);
            if (showDoseGroup) {
                bevacizumabDoseGroup.check(R.id.chipBev75);
            }
        });
        bevacizumabDoseGroup.check(R.id.chipBev75);
        bevacizumabDoseGroup.setVisibility(View.GONE);
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
        ChipGroup bevacizumabDoseGroup = findViewById(R.id.bevacizumabDoseGroup);

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
        boolean bevacizumabYes = "Yes".equalsIgnoreCase(bevacizumabOption.getText().toString().trim());
        if (bevacizumabYes && bevacizumabDoseGroup.getCheckedChipId() == View.NO_ID) {
            errorMessage.setText("Pilih dosis bevacizumab");
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }

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
        double GFRBulatFinal = pembulatanDuaDesimal(GFR);

        //menampilkan GFR
        TextView viewGFR = findViewById(R.id.GFR_Normal);
        viewGFR.setText(GFRBulatFinal + " mL/min");

        //Hitung GFR Obese
        double GFRobese = hitungGFRobese(usiaPasien, beratBadan, tinggiBadan, serumKreatinin);
        double GFRObeseBulatFinal = pembulatanDuaDesimal(GFRobese);

        //menampilkan GFR Obese
        TextView viewGFRobese = findViewById(R.id.GFR_Obese);
        viewGFRobese.setText(GFRObeseBulatFinal + " mL/min");

        //menghitung dosis Paclitaxel = LPT x 175 mg
        double dosisPaclitaxel = LPT * 175;

        //menampilkan kadar Paclitaxel
        TextView kadarPaclitaxel = findViewById(R.id.paclitaxel);
        kadarPaclitaxel.setText((int) dosisPaclitaxel + " mg");

        //menghitung dosis Carboplatin = (GFR + 25) x AUC
        double dosisCarboplatin = (GFR + 25) * auc;

        //menampilkan kadar Carboplatin Normal
        TextView kadarCarboplatin = findViewById(R.id.carboplatin);
        kadarCarboplatin.setText((int) dosisCarboplatin + " mg");

        //menghitung dosis Carboplatin Obese = (GFR Obese + 25) x AUC
        double dosisCarboplatinObese = (GFRobese + 25) * auc;

        //menampilkan kadar Carboplatin Obese
        TextView kadarCarboplatinObese = findViewById(R.id.carboplatinObese);
        kadarCarboplatinObese.setText((int) dosisCarboplatinObese + " mg");

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
    }

    /**
     * Fungsi tombol reset
     */
    public void klikReset(View view) {

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
        bevacizumabOption.setText("No", false);

        ChipGroup bevacizumabDoseGroup = findViewById(R.id.bevacizumabDoseGroup);
        bevacizumabDoseGroup.check(R.id.chipBev75);
        bevacizumabDoseGroup.setVisibility(View.GONE);

        TextView errorMessage = findViewById(R.id.errorMessage);
        errorMessage.setVisibility(View.GONE);
        errorMessage.setText("");
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
