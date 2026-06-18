# BEP Renal Adjustment Design

## Goal
Tambahkan penyesuaian dosis berbasis GFR pada menu `BEP` dengan sumber GFR aktif mengikuti `selectedGfr` yang sudah dipakai regimen lain.

## Scope
- Terapkan aturan renal untuk `Bleomycin`, `Etoposide`, dan `Cisplatin` di `BEP`.
- Gunakan `GfrUtils.getSelectedGfr(gfr, gfrObese, isGfrObese)` agar mode obese mempengaruhi penyesuaian.
- Tampilkan perubahan dosis dengan pola yang sudah dipakai `EMAEP`.
- Tambahkan catatan aturan GFR di layout `BEP`.
- Rapikan reset agar output kembali ke default.

## Rules
- `Bleomycin`
  - `GFR 10-49`: gunakan `75%` dosis
  - `GFR < 10`: gunakan `50%` dosis
- `Etoposide`
  - sama seperti `EMA-EP`
  - `GFR 10-49`: gunakan `75%` dosis
  - `GFR < 10`: gunakan `50%` dosis
- `Cisplatin`
  - sama seperti `EMA-EP`
  - `GFR 50-59`: gunakan `75%` dosis
  - `GFR 40-49`: gunakan `50%` dosis
  - `GFR < 40`: tampilkan `Ganti carboplatin`

## Implementation Notes
- Pertahankan ID view yang sudah ada.
- Hindari refactor lintas-regimen; duplikasi helper kecil di `BEP` masih diterima untuk menjaga scope.
- Pembulatan dosis adjusted mengikuti `Math.round(...)` ke bilangan bulat terdekat.

## Testing
- Tambah unit test JVM untuk helper multiplier dan pembulatan adjusted dose di `BEP`.
- Verifikasi build lewat `./gradlew testDebugUnitTest` dan `./gradlew assembleDebug` bila environment Java kompatibel.
