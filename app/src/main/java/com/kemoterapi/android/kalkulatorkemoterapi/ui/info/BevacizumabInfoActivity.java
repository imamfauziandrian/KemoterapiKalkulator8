package com.kemoterapi.android.kalkulatorkemoterapi.ui.info;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.kemoterapi.android.kalkulatorkemoterapi.R;

public class BevacizumabInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bevacizumab_info);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        TextView titleView = findViewById(R.id.pageTitle);
        TextView bodyView = findViewById(R.id.pageBody);
        TextView refView = findViewById(R.id.pageReferences);

        titleView.setText("Bevacizumab");
        bodyView.setText(HtmlCompat.fromHtml(
                "<b>Ca Ovarium monoterapi pasca kombinasi dengan Paclitaxel Carboplatin</b><br><br>" +
                        "<b>7,5 mg/kgBB (ICON7 Trial)</b><br>" +
                        "Diberikan bersamaan saat kemo Paclitaxel dan Carboplatin, kemudian dilanjutkan maintenance 3 minggu sekali hingga 18 kali total atau 12 kali maintenance monoterapi pasca kemoterapi kombinasi.<br><br>" +
                        "<b>15 mg/kgBB (GOG218)</b><br>" +
                        "Diberikan bersamaan saat kemo Paclitaxel dan Carboplatin, kemudian dilanjutkan maintenance 3 minggu sekali hingga 22 kali total atau 16 kali maintenance monoterapi pasca kemoterapi kombinasi.",
                HtmlCompat.FROM_HTML_MODE_LEGACY));
        refView.setText(
                "1. Standard chemotherapy with or without bevacizumab for women with newly diagnosed ovarian cancer (ICON7): overall survival results of a phase 3 randomised trial. Lancet Oncol. 2015 Aug;16(8):928-36. doi: 10.1016/S1470-2045(15)00086-8. Epub 2015 Jun 23. PMID: 26115797; PMCID: PMC4648090.\n\n" +
                        "2. Final Overall Survival of a Randomized Trial of Bevacizumab for Primary Treatment of Ovarian Cancer. J Clin Oncol. 2019 Sep 10;37(26):2317-2328. doi: 10.1200/JCO.19.01009. Epub 2019 Jun 19. PMID: 31216226; PMCID: PMC6879307.");
    }
}
