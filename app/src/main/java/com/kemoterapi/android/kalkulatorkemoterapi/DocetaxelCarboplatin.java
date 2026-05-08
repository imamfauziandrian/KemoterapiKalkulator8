package com.kemoterapi.android.kalkulatorkemoterapi;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.Math;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class DocetaxelCarboplatin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docetaxel_carboplatin);

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
        viewIMT.setText((double) isiIMTbulatFinal + " kg/m2");

        //Hitung LPT
        double LPT = hitungLPT(beratBadan, tinggiBadan);
        double luasPermukaanTubuhBulatFinal = pembulatanDuaDesimal(LPT);

        //menampilkan Luas Permukaan Tubuh
        TextView viewLPT = (TextView) findViewById(R.id.LuasPermukaanTubuh);
        viewLPT.setText((double) luasPermukaanTubuhBulatFinal + " m2");

        //Hitung GFR
        double GFR = hitungGFR(usiaPasien, beratBadan, serumKreatinin);
        double GFRBulatFinal = pembulatanDuaDesimal(GFR);

        //menampilkan GFR
        TextView viewGFR = (TextView) findViewById(R.id.GFR_Normal);
        viewGFR.setText((double) GFRBulatFinal + " mL/min");

        //Hitung GFR Obese
        double GFRobese = hitungGFRobese(usiaPasien, beratBadan, tinggiBadan, serumKreatinin);
        double GFRObeseBulatFinal = pembulatanDuaDesimal(GFRobese);

        //menampilkan GFR Obese
        TextView viewGFRobese = (TextView) findViewById(R.id.GFR_Obese);
        viewGFRobese.setText((double) GFRObeseBulatFinal + " mL/min");


        //menghitung dosis Docetaxel = LPT x 75 mg
        double dosisDocetaxel = LPT * 75;

        //menampilkan kadar Docetaxel
        TextView kadarDocetaxel = (TextView) findViewById(R.id.docetaxel);
        kadarDocetaxel.setText((int) dosisDocetaxel + " mg");


        //menghitung dosis Carboplatin = (GFR + 25) x 5
        double dosisCarboplatin = (GFR + 25) * 5;

        //menampilkan kadar Carboplatin Normal
        TextView kadarCarboplatin = (TextView) findViewById(R.id.carboplatin);
        kadarCarboplatin.setText((int) dosisCarboplatin + " mg");

        //menghitung dosis Carboplatin Obese = (GFR Obese + 25) x 5
        double dosisCarboplatinObese = (GFRobese + 25) * 5;

        //menampilkan kadar Carboplatin Obese
        TextView kadarCarboplatinObese = (TextView) findViewById(R.id.carboplatinObese);
        kadarCarboplatinObese.setText((int) dosisCarboplatinObese + " mg");

        //menghitung dosis Carboplatin GFR 40-60 = 250 x LPT
        double dosisCarboplatinMildAki = 250 * LPT;

        //menampilkan kadar Carboplatin Obese
        TextView kadarCarboplatinMildAki = (TextView) findViewById(R.id.carboplatin4060);
        kadarCarboplatinMildAki.setText((int) dosisCarboplatinMildAki + " mg");

        //menghitung dosis Carboplatin GFR 40 = 200 x LPT
        double dosisCarboplatinSevereAki = 200 * LPT;

        //menampilkan kadar Carboplatin Obese
        TextView kadarCarboplatinSevereAki = (TextView) findViewById(R.id.carboplatin40);
        kadarCarboplatinSevereAki.setText((int) dosisCarboplatinSevereAki + " mg");

    }

    /**
     * Fungsi tombol reset
     */

    public void klikReset (View view) {

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