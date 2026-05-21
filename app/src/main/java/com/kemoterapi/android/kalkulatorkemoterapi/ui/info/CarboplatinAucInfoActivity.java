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

public class CarboplatinAucInfoActivity extends AppCompatActivity {

    private static final String[] TAB_TITLES = {"Panduan AUC", "Ovarium Epitelial atau Endometrium", "Serviks"};

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
                                "Ovarium Epitelial atau Endometrium",
                                "<b>Kanker Ovarium Epitelial atau Kanker Endometrium</b><br>" +
                                        "Pasien kanker ovarium epitelial atau kanker endometrium yang hipersensitif dengan paclitaxel atau tidak mampu menerima kombinasi paclitaxel-carboplatin, maka carboplatin single-agent AUC 5-6 tiap 3 minggu dapat dipertimbangkan.<br><br>" +
                                        "<small><b>Referensi</b><br>" +
                                        "1. Falandry C, Rousseau F, Mouret-Reynier MA, Tinquaut F, Lorusso D, Herrstedt J, Savoye AM, Stefani L, Bourbouloux E, Sverdlin R, D'Hondt V, Lortholary A, Brachet PE, Zannetti A, Malaurie E, Venat-Bouvet L, Tredan O, Mourey L, Pujade-Lauraine E, Freyer G; Groupe d’Investigateurs Nationaux pour l’Étude des Cancers de l’Ovaire et du sein (GINECO). Efficacy and Safety of First-line Single-Agent Carboplatin vs Carboplatin Plus Paclitaxel for Vulnerable Older Adult Women With Ovarian Cancer: A GINECO/GCIG Randomized Clinical Trial. JAMA Oncol. 2021 Jun 1;7(6):853-861. doi:10.1001/jamaoncol.2021.0696. Erratum in: JAMA Oncol. 2021 Jun 1;7(6):945. doi:10.1001/jamaoncol.2021.2471. PMID:33885718; PMCID:PMC8063137.<br><br>" +
                                        "2. Barretina-Ginesta MP, Quindos M, Alarcon JD, Esteban C, Gaba L, Gomez C, Fidalgo JAP, Romero I, Santaballa A, Rubio-Perez MJ. SEOM-GEICO clinical guidelines on endometrial cancer (2021). Clin Transl Oncol. 2022 Apr;24(4):625-634. doi:10.1007/s12094-022-02799-7. Epub 2022 Mar 21. PMID:35312947; PMCID:PMC8986694.</small>");
                    default:
                        return InfoPageFragment.newInstance(
                                "Serviks",
                                "<b>Kanker Serviks</b><br>" +
                                        "Pasien kanker serviks yang membutuhkan radiosensitiser tetapi ada kontraindikasi menggunakan cisplatin dapat menggunakan Carboplatin AUC 2 setiap minggu.<br><br>" +
                                        "<small><b>Referensi</b><br>" +
                                        "1. Katanyoo K, Tangjitgamol S, Chongthanakorn M, Tantivatana T, Manusirivithaya S, Rongsriyam K, Cholpaisal A. Treatment outcomes of concurrent weekly carboplatin with radiation therapy in locally advanced cervical cancer patients. Gynecol Oncol. 2011 Dec;123(3):571-6. doi:10.1016/j.ygyno.2011.09.001. Epub 2011 Sep 28. PMID:21955483.</small>");
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
