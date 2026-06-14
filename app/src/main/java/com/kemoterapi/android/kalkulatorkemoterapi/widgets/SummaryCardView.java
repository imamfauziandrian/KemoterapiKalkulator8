package com.kemoterapi.android.kalkulatorkemoterapi.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.kemoterapi.android.kalkulatorkemoterapi.R;

public class SummaryCardView extends MaterialCardView {

    private final MaterialCardView gfrNormalCard;
    private final MaterialCardView gfrObeseCard;

    public SummaryCardView(@NonNull Context context) {
        this(context, null);
    }

    public SummaryCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, com.google.android.material.R.attr.materialCardViewStyle);
    }

    public SummaryCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_summary_card_content, this, true);

        gfrNormalCard = findViewById(R.id.cardGfrNormal);
        gfrObeseCard = findViewById(R.id.cardGfrObese);

        float corner = dpToPx(12);
        gfrNormalCard.setShapeAppearanceModel(
                gfrNormalCard.getShapeAppearanceModel().toBuilder()
                        .setTopLeftCornerSize(0f)
                        .setTopRightCornerSize(0f)
                        .setBottomLeftCornerSize(corner)
                        .setBottomRightCornerSize(0f)
                        .build());
        gfrObeseCard.setShapeAppearanceModel(
                gfrObeseCard.getShapeAppearanceModel().toBuilder()
                        .setTopLeftCornerSize(0f)
                        .setTopRightCornerSize(0f)
                        .setBottomLeftCornerSize(0f)
                        .setBottomRightCornerSize(corner)
                        .build());
    }

    public void setGfrHighlight(boolean obese) {
        applyBlockHighlight(gfrNormalCard, !obese);
        applyBlockHighlight(gfrObeseCard, obese);
    }

    public void clearGfrHighlight() {
        applyBlockHighlight(gfrNormalCard, false);
        applyBlockHighlight(gfrObeseCard, false);
    }

    private void applyBlockHighlight(MaterialCardView card, boolean active) {
        if (card == null) {
            return;
        }

        int backgroundColor = active
                ? MaterialColors.getColor(this, com.google.android.material.R.attr.colorPrimaryContainer)
                : Color.TRANSPARENT;
        int strokeColor = MaterialColors.getColor(this, com.google.android.material.R.attr.colorPrimary);

        card.setCardBackgroundColor(backgroundColor);
        card.setStrokeWidth(active ? Math.round(dpToPx(1)) : 0);
        card.setStrokeColor(ColorStateList.valueOf(strokeColor));
    }

    private float dpToPx(int dp) {
        return dp * getResources().getDisplayMetrics().density;
    }
}
