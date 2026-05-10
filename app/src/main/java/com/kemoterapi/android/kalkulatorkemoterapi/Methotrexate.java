package com.kemoterapi.android.kalkulatorkemoterapi;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.Math;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class Methotrexate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_methotrexate);

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

        //menghitung dosis Mtx LD = 0,4 mg x berat badan
        double dosisMtxLD = beratBadan * 0.4;

        //menampilkan dosis Mtx Ld
        TextView kadarMtxLd = (TextView) findViewById(R.id.mtxLD);
        kadarMtxLd.setText((int) dosisMtxLD + " mg");


        //menghitung dosis Mtx HD = 1 mg x berat badan

        //menampilkan dosis Mtx HD
        TextView kadarMtxHd = (TextView) findViewById(R.id.mtxHD);
        kadarMtxHd.setText((int) beratBadan + " mg");

        //menghitung dosis Leucovorin = 0,1 x BB
        double dosisLeucovorin = beratBadan * 0.1;

        //menampilkan kadar Leucovorin
        TextView kadarLeucovorin = (TextView) findViewById(R.id.leucovorin);
        kadarLeucovorin.setText((int) dosisLeucovorin + " mg");

        //menghitung dosis Dactinomycin = 1,25 x BB
        double dosisDactinomycin = 1.25 * beratBadan;

        //menampilkan dosis Dactinomycin
        TextView kadarDactinomycin = (TextView) findViewById(R.id.dactinomicyn);
        kadarDactinomycin.setText((int) dosisDactinomycin + " mg");



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
