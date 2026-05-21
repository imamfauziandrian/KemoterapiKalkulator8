package com.kemoterapi.android.kalkulatorkemoterapi;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Spanned;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.textfield.TextInputLayout;

public class Bevacizumab extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bevacizumab);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        AutoCompleteTextView bevacizumabOption = findViewById(R.id.bevacizumabOption);
        CharSequence[] options = new CharSequence[]{
                buildBevacizumabOption("7,5"),
                buildBevacizumabOption("15")
        };
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                options);
        bevacizumabOption.setAdapter(adapter);
        bevacizumabOption.setOnItemClickListener((parent, view, position, id) -> {
            CharSequence selected = (CharSequence) parent.getItemAtPosition(position);
            bevacizumabOption.setText(selected, false);
        });
        bevacizumabOption.setText(options[0], false);

        applyHighlight(findViewById(R.id.cardBevacizumab), false);
    }

    public void klikHitung(View view) {
        TextInputLayout tilBerat = findViewById(R.id.tilBerat);
        TextInputLayout tilBevacizumab = findViewById(R.id.tilBevacizumab);
        TextView errorMessage = findViewById(R.id.errorMessage);

        clearErrors(tilBerat, tilBevacizumab);
        errorMessage.setVisibility(View.GONE);
        errorMessage.setText("");

        EditText berat = findViewById(R.id.beratBadan);
        if (TextUtils.isEmpty(berat.getText())) {
            tilBerat.setError("Berat wajib diisi");
            errorMessage.setText("Berat wajib diisi");
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }
        double beratBadan = Double.parseDouble(berat.getText().toString());

        AutoCompleteTextView bevacizumabOption = findViewById(R.id.bevacizumabOption);
        if (TextUtils.isEmpty(bevacizumabOption.getText())) {
            tilBevacizumab.setError("Dosis Bevacizumab wajib dipilih");
            errorMessage.setText("Dosis Bevacizumab wajib dipilih");
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }

        String selection = bevacizumabOption.getText().toString().trim();
        double dosisPerKg = selection.startsWith("15") ? 15 : 7.5;
        int totalDose = hitungDosisBevacizumab(beratBadan, dosisPerKg);

        TextView subtitle = findViewById(R.id.bevacizumabSubtitle);
        TextView dose = findViewById(R.id.bevacizumabDose);
        subtitle.setText("Dosis berdasarkan BB x " + selection);
        dose.setText(buildDoseText(totalDose, "mg"));
        applyHighlight(findViewById(R.id.cardBevacizumab), true);
    }

    public void klikReset(View view) {
        EditText berat = findViewById(R.id.beratBadan);
        berat.setText(null);

        AutoCompleteTextView bevacizumabOption = findViewById(R.id.bevacizumabOption);
        bevacizumabOption.setText(buildBevacizumabOption("7,5"), false);

        clearErrors(findViewById(R.id.tilBerat), findViewById(R.id.tilBevacizumab));

        TextView errorMessage = findViewById(R.id.errorMessage);
        errorMessage.setVisibility(View.GONE);
        errorMessage.setText("");

        TextView subtitle = findViewById(R.id.bevacizumabSubtitle);
        TextView dose = findViewById(R.id.bevacizumabDose);
        subtitle.setText("Dosis berdasarkan BB");
        dose.setText("0");
        applyHighlight(findViewById(R.id.cardBevacizumab), false);
    }

    private void clearErrors(TextInputLayout... layouts) {
        for (TextInputLayout layout : layouts) {
            layout.setError(null);
        }
    }

    private CharSequence buildBevacizumabOption(String dose) {
        SpannableStringBuilder builder = new SpannableStringBuilder(dose);
        int unitStart = builder.length();
        builder.append(" mg/kgBB");
        builder.setSpan(new RelativeSizeSpan(0.72f), unitStart, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    private static CharSequence buildDoseText(int dose, String unit) {
        SpannableStringBuilder builder = new SpannableStringBuilder(String.valueOf(dose));
        int unitStart = builder.length();
        builder.append(" ").append(unit);
        builder.setSpan(new RelativeSizeSpan(0.72f), unitStart, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    static int hitungDosisBevacizumab(double beratBadan, double dosisPerKg) {
        return (int) Math.round(beratBadan * dosisPerKg);
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
}
