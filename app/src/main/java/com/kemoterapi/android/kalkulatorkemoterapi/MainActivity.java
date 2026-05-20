package com.kemoterapi.android.kalkulatorkemoterapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //klik ke halaman Pacli Carbo
    public void klikPacliCarbo(View view){
        Intent intent = new Intent(this, PaclitaxelCarboplatin.class);
        startActivity(intent);
    }

    //klik ke halaman Cisplatin
    public void klikCisplatin(View view){
        Intent intent = new Intent(this, Cisplatin.class);
        startActivity(intent);
    }

    //klik ke halaman Gemci Carbo
    public void klikGemciCarbo(View view){
        Intent intent = new Intent(this, GemcitabinCarboplatin.class);
        startActivity(intent);
    }

    //klik ke halaman Doce Carbo
    public void klikDoceCarbo(View view){
        Intent intent = new Intent(this, DocetaxelCarboplatin.class);
        startActivity(intent);
    }


    //klik ke halaman MTX
    public void klikMTX(View view){
        Intent intent = new Intent(this, Methotrexate.class);
        startActivity(intent);
    }


    //klik ke halaman EMACO
    public void klikEmaco(View view){
        Intent intent = new Intent(this, EMACO.class);
        startActivity(intent);
    }

    //klik ke halaman BEP
    public void klikBEP(View view){
        Intent intent = new Intent(this, BEP.class);
        startActivity(intent);
    }

    //klik ke halaman EMAEP
    public void klikEmaEP(View view){
        Intent intent = new Intent(this, EMAEP.class);
        startActivity(intent);
    }
}
