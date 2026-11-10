# Palet tema Kemokulator

Warna aplikasi berasal langsung dari logo (`docs/branding/kemokulator-source.png`):

| Nama | Hex | Peran |
| --- | --- | --- |
| Navy | `#114769` | Warna dominan logo, dipakai sebagai **primary** |
| Teal | `#37BB96` | Aksen logo, dipakai sebagai **tertiary** |
| Putih | `#FFFFFF` | Latar logo |

Token `md_theme_*` di `app/src/main/res/values/colors.xml` diturunkan dari dua
warna di atas memakai tonal palette Material 3 (HCT), bukan dipilih manual:

- `primary` = hue navy pada tone 40 → `#114769` (persis warna logo)
- `tertiary` = hue teal pada tone 40 → `#006C53` (teal digelapkan agar teks
  di atas surface tetap lolos kontras WCAG AA)
- `secondary` = hue navy dengan chroma rendah (aksen netral)
- `neutral`/`neutral-variant` = chroma sangat rendah dengan hue navy, sehingga
  surface terang `#F9F9FC` dan gelap `#111416` tetap terasa senada dengan logo

## Pemetaan peran

| Peran Material 3 | Terang | Gelap |
| --- | --- | --- |
| `colorPrimary` | `#114769` | `#9ECBF4` |
| `colorOnPrimary` | `#FFFFFF` | `#003350` |
| `colorPrimaryContainer` | `#CCE5FF` | `#164A6D` |
| `colorOnPrimaryContainer` | `#001E31` | `#CCE5FF` |
| `colorSecondary` | `#50606F` | `#B8C8D9` |
| `colorTertiary` | `#006C53` | `#5EDCB5` |
| `colorSurface` | `#F9F9FC` | `#111416` |
| `colorOnSurface` | `#1A1C1E` | `#E2E2E5` |
| `colorOnSurfaceVariant` | `#42474E` | `#C2C7CE` |
| `colorSurfaceContainerLow` | `#F3F3F6` | `#1A1C1E` |
| `colorOutline` | `#72787E` | `#8C9198` |
| `colorOutlineVariant` | `#C2C7CE` | `#42474E` |
| `colorError` | `#BA1A1A` | `#FFB4AB` |

## Aturan pemakaian

- Layout dan drawable **hanya** memakai atribut tema (`?attr/colorPrimary`,
  `?attr/colorSurfaceContainerLow`, dst). Tidak ada warna heksadesimal di
  `res/layout` atau `res/drawable`.
- Toolbar memakai `colorPrimaryContainer` + `colorOnPrimaryContainer`, sehingga
  ikon status bar tetap terbaca di light mode (`windowLightStatusBar=true`).
- Garis aksen di kartu beranda memakai `colorTertiary` agar teal dari logo tetap
  tampil sebagai aksen.
- Teks sekunder memakai `colorOnSurfaceVariant` (kontras ≥ 7:1 pada surface),
  bukan `colorOutline`, karena `colorOutline` hanya untuk garis.
