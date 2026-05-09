package com.kemoterapi.android.kalkulatorkemoterapi.ui.info;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kemoterapi.android.kalkulatorkemoterapi.R;

public class AucInfoActivity extends AppCompatActivity {

    private static final String[] TAB_TITLES = {"Panduan AUC", "Ovarium", "Endometrium"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auc_info);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return InfoPageFragment.newInstance(
                                "Panduan AUC",
                                "<b>Panduan singkat</b><br>" +
                                        "Pilih AUC sesuai kondisi klinis pasien. Berikut ringkasan paling praktis untuk aplikasi ini:<br><br>" +
                                        "<b>AUC 6 — Intensitas lebih tinggi</b>" +
                                        "<ul>" +
                                        "<li><b>Keuntungan:</b> Umumnya memberikan konsentrasi serum yang lebih tinggi dan optimal untuk efektivitas.</li>" +
                                        "<li><b>Kerugian:</b> Risiko pengurangan dosis dan toksisitas hematologi (neutropenia, anemia) lebih tinggi dibandingkan AUC 5.</li>" +
                                        "<li><b>Penggunaan:</b> Standar untuk pasien muda dan sehat, dan sering digunakan dalam pengaturan adjuvan awal.</li>" +
                                        "</ul>" +
                                        "<b>AUC 5 — Toksisitas lebih rendah</b>" +
                                        "<ul>" +
                                        "<li><b>Keuntungan:</b> Pengurangan toksisitas yang menjanjikan, memungkinkan pasien menyelesaikan 6 siklus penuh tanpa penundaan atau pengurangan dosis.</li>" +
                                        "<li><b>Kerugian:</b> Potensi tingkat respons lengkap patologis yang lebih rendah dalam beberapa penelitian dibandingkan AUC 6.</li>" +
                                        "<li><b>Penggunaan:</b> Lebih disukai untuk kemoterapi neoadjuvan (NACT) agar pasien dapat mentolerir operasi (debulking interval). Juga disukai untuk pasien yang lemah atau lanjut usia.</li>" +
                                        "</ul>");
                    case 1:
                        return InfoPageFragment.newInstance(
                                "Ovarium",
                                "<b>Pemilihan dosis</b><br>" +
                                        "• <b>Kanker ovarium</b><br>" +
                                        "• <b>Paclitaxel 175 mg/m2 AUC 5/6 (ICON3 Trial)</b><sup>1</sup><br><br>" +
                                        "<b>Referensi</b><br>" +
                                        "1. International Collaborative Ovarian Neoplasm Group. Paclitaxel plus carboplatin versus standard chemotherapy with either single-agent carboplatin or cyclophosphamide, doxorubicin, and cisplatin in women with ovarian cancer: the ICON3 randomised trial. Lancet. 2002 Aug 17;360(9332):505-15. doi:10.1016/S0140-6736(02)09738-6. Erratum in: Lancet. 2003 Feb 22;361(9358):706. PMID:12241653.");
                    default:
                        return InfoPageFragment.newInstance(
                                "Endometrium",
                                "<b>Pemilihan dosis</b><br>" +
                                        "• <b>Kanker endometrium</b><br>" +
                                        "• <b>Paclitaxel 175 mg/m2 AUC 5/6 (GOG209 Trial)</b><sup>1</sup><br><br>" +
                                        "<b>Referensi</b><br>" +
                                        "1. Fleming GF, Brady MF, Pettit JE, et al. Carboplatin and paclitaxel for advanced endometrial cancer: final overall survival and adverse event analysis of a phase III trial (NRG Oncology/GOG0209). J Clin Oncol. 2020;38(33):3841-3850. doi:10.1200/JCO.20.01076.");
                }
            }

            @Override
            public int getItemCount() {
                return TAB_TITLES.length;
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(TAB_TITLES[position])).attach();
    }
}
