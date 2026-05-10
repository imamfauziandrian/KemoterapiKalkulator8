package com.kemoterapi.android.kalkulatorkemoterapi;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

final class PatientInputCache {

    private static String usia;
    private static String beratBadan;
    private static String tinggiBadan;
    private static String serumKreatinin;

    private PatientInputCache() {
    }

    static void bind(EditText usiaField,
                     EditText beratField,
                     EditText tinggiField,
                     EditText serumField) {
        restore(usiaField, beratField, tinggiField, serumField);

        attachWatcher(usiaField, Field.USIA);
        attachWatcher(beratField, Field.BERAT);
        attachWatcher(tinggiField, Field.TINGGI);
        attachWatcher(serumField, Field.SERUM);
    }

    static void clear() {
        usia = null;
        beratBadan = null;
        tinggiBadan = null;
        serumKreatinin = null;
    }

    private static void restore(EditText usiaField,
                                EditText beratField,
                                EditText tinggiField,
                                EditText serumField) {
        setTextIfDifferent(usiaField, usia);
        setTextIfDifferent(beratField, beratBadan);
        setTextIfDifferent(tinggiField, tinggiBadan);
        setTextIfDifferent(serumField, serumKreatinin);
    }

    private static void attachWatcher(EditText field, Field targetField) {
        field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                set(targetField, TextUtils.isEmpty(s) ? null : s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private static void set(Field targetField, String value) {
        switch (targetField) {
            case USIA:
                usia = value;
                break;
            case BERAT:
                beratBadan = value;
                break;
            case TINGGI:
                tinggiBadan = value;
                break;
            case SERUM:
                serumKreatinin = value;
                break;
        }
    }

    private static void setTextIfDifferent(EditText field, String value) {
        CharSequence currentText = field.getText();
        if (TextUtils.equals(currentText, value)) {
            return;
        }
        field.setText(value);
    }

    private enum Field {
        USIA,
        BERAT,
        TINGGI,
        SERUM
    }
}
