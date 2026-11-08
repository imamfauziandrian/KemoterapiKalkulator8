# Memperbarui Kemokulator di Google Play Store

Panduan untuk memperbarui aplikasi yang sudah ada. Referensi resmi diperiksa pada **8 November 2026**. Nama menu dapat mengikuti bahasa dan tampilan Play Console akun Anda.

## 1. Kenali perubahan dalam proyek

| Bagian | Lokasi / nilai |
| --- | --- |
| Nama aplikasi | `Kemokulator`, di `app/src/main/res/values/strings.xml` |
| Judul halaman utama | `app/src/main/res/layout/activity_main.xml`, memakai `@string/app_name` |
| Nama proyek Android Studio | `settings.gradle` |
| Tema terang dan gelap | `Theme.Kemokulator`, di kedua berkas `themes.xml` dan manifest |
| Logo sumber asli | [`branding/kemokulator-source.png`](branding/kemokulator-source.png), salinan utuh `Logo Kemokulator-02.png` |
| Ikon launcher Android 5–7.1 | `app/src/main/res/mipmap-*/ic_launcher.png` dan `ic_launcher_round.png` |
| Ikon adaptif Android 8+ | `app/src/main/res/mipmap-anydpi-v26/ic_launcher*.xml`, foreground di lima folder kepadatan, latar putih |
| Ikon bertema Android 13+ | Lapisan `monochrome` memakai transparansi foreground logo yang sama |
| Ikon untuk diunggah ke Play Console | [`../app/src/main/ic_launcher-playstore.png`](../app/src/main/ic_launcher-playstore.png) |
| Versi yang disiapkan | `versionCode 11`, `versionName '1.0.2'`, di `app/build.gradle` |
| Identitas aplikasi | `com.kemoterapi.android.kalkulatorkemoterapi` |

Ikon launcher tersedia untuk `mdpi`, `hdpi`, `xhdpi`, `xxhdpi`, dan `xxxhdpi`. Foreground adaptif berukuran 108 dp dengan simbol di tengah sepanjang maksimal 64 dp, di dalam ruang aman 66 dp. Ikon Android lama berukuran 48/72/96/144/192 px. XML foreground lama bergambar robot Android sudah dihapus. Lihat [panduan ikon adaptif Android](https://developer.android.com/develop/ui/compose/system/icon_design_adaptive).

Package Java, `applicationId`, dan `namespace` tetap memakai identitas lama. Nama folder checkout dan dokumen rencana historis juga dapat memuat nama lama; keduanya bukan label aplikasi. **Jangan mengganti identitas paket untuk rebranding ini**: update harus memakai package yang sama, kode versi lebih tinggi, dan signing yang sesuai dengan aplikasi sebelumnya. Lihat [persyaratan pembaruan aplikasi](https://support.google.com/googleplay/android-developer/answer/9859350).

## 2. Periksa aplikasi yang benar dan nomor versi

1. Masuk ke [Google Play Console](https://play.google.com/console/) dengan akun pemilik/pengelola aplikasi.
2. Buka **aplikasi lama yang sudah terdaftar**, lalu cocokkan package dengan `com.kemoterapi.android.kalkulatorkemoterapi`. Jangan membuat aplikasi baru untuk perubahan nama ini. Jika package di Console berbeda, hentikan persiapan upload dan cocokkan dengan source rilis lama terlebih dahulu.
3. Buka **Test and release → Latest releases and bundles** dan periksa kode versi yang pernah diunggah, termasuk track pengujian.
4. Proyek sebelumnya memakai kode `10`; perubahan ini menyiapkan `11` / `1.0.2`. Gunakan `11` hanya jika belum pernah digunakan dan lebih tinggi daripada kode versi sebelumnya di Console. Jika perlu, naikkan lagi `versionCode` di `app/build.gradle`, lalu build ulang. `versionName` adalah versi yang terlihat oleh pengguna.
5. Periksa persyaratan target API saat akan upload. Proyek memakai `compileSdkVersion 36`, `targetSdkVersion 36`, dan `minSdkVersion 21`. Pada tanggal panduan ini, pembaruan aplikasi ponsel wajib menargetkan minimal API 36 sejak 31 Agustus 2026. Lihat [persyaratan target API Google Play](https://support.google.com/googleplay/android-developer/answer/11926878).

Rujukan untuk lokasi bundle dan aturan kode versi: [mengelola aplikasi dan app bundle](https://support.google.com/googleplay/android-developer/answer/9859152).

## 3. Build dan periksa aplikasi

1. Buka folder proyek di Android Studio dan jalankan Gradle Sync.
2. Gunakan JDK 17 atau 21 untuk Gradle proyek ini. Verifikasi perubahan ini memakai JDK 21 bawaan Android Studio. Di macOS dengan lokasi instalasi standar:

   ```sh
   export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
   ```

   Terminal komputer ini menggunakan Java 26 secara default; pilih JDK yang didukung wrapper Gradle 8.13 sebelum menjalankan perintah build.

3. Dari direktori proyek, gunakan wrapper:

   ```sh
   ./gradlew assembleDebug test
   ```

4. APK debug berada di `app/build/outputs/apk/debug/app-debug.apk`. Gunakan pada emulator atau perangkat pengujian. Untuk menguji pembaruan atas instalasi dari Play Store, gunakan jalur **Internal testing** di langkah 6 agar penandatanganan dilakukan oleh Google Play.
5. Periksa ikon serta label **Kemokulator** di launcher, judul halaman utama, daftar aplikasi di Settings, dan tampilan saat membuka aplikasi. Periksa tema terang/gelap, bentuk ikon bulat/kotak, dan ikon bertema jika launcher mendukungnya. Pastikan semua layar kalkulator masih bisa dibuka. Sertakan Android 5–7.1 jika tersedia untuk memeriksa fallback ikon lama.

Catatan pengujian: `./gradlew test` menjalankan unit test JVM. Suite `connectedAndroidTest` memerlukan perangkat dan masih memiliki assertion package lama `com.harinugroho.android.kemoterapikalkulator` di `ExampleInstrumentedTest.java`; assertion itu perlu disesuaikan dengan package aktual sebelum memakai suite tersebut sebagai syarat rilis.

Hasil verifikasi perubahan branding pada 8 November 2026:

| Pemeriksaan | Hasil |
| --- | --- |
| `./gradlew assembleDebug` | Berhasil; APK di `app/build/outputs/apk/debug/app-debug.apk` |
| `./gradlew bundleRelease` | Berhasil, termasuk `lintVitalRelease`; AAB **unsigned** di `app/build/outputs/bundle/release/app-release.aab` |
| Isi APK | Label Kemokulator pada seluruh konfigurasi bahasa, package tetap, versi `11` / `1.0.2`, ikon legacy/adaptif/bulat/monochrome tersedia |
| Aset logo | 15 PNG launcher sesuai ukuran, PNG Play Store 512 px, dan salinan logo sumber identik dengan file asli |
| Unit test debug | 13 dari 15 lulus; dua kegagalan identik juga direproduksi dari commit sebelum perubahan (`5e8d895`) |
| Emulator/perangkat | Belum diuji; emulator gagal mulai karena ruang disk tidak cukup |
| Signing dan Play Console | Belum dilakukan; perlu keystore terdaftar dan akses akun Console |

Dua unit test yang sudah gagal sebelum rebranding adalah `bepAdjustedDose_roundsToNearestWholeNumber` (ekspektasi `138`, hasil `137`) dan `methotrexateSquaredUnitText_formatsUnitAndSuperscript` (`SpannableStringBuilder.length` tidak tersedia pada stub Android untuk JVM). Kode kalkulator dan kedua test tersebut tidak diubah oleh perubahan branding ini. Tinjau kegagalan tersebut sebelum rilis produksi; keberhasilan build bukan berarti seluruh test lulus. Hasil kegagalan dapat dibuka di `app/build/reports/tests/testDebugUnitTest/index.html` setelah menjalankan test.

## 4. Buat Android App Bundle yang ditandatangani

1. Siapkan **keystore/upload key yang terdaftar untuk aplikasi ini**, alias, dan password-nya. Jangan membuat kunci pengganti hanya karena nama aplikasi berubah. Jika memakai Play App Signing, cocokkan sertifikat upload key dengan informasi **App integrity / App signing** di Console. Jika kunci hilang, selesaikan pemulihan/reset upload key melalui alur resmi sebelum upload.
2. Di Android Studio, pilih **Build → Generate Signed Bundle / APK…**.
3. Pilih **Android App Bundle**, lalu **Next**.
4. Pilih modul `app`, isi lokasi keystore yang benar, alias, dan password.
5. Pilih build variant **release**, tentukan folder output, lalu jalankan pembuatan bundle.
6. Simpan lokasi `.aab` yang ditampilkan Android Studio. Jika output dipilih ke `app/release/`, berkas biasanya bernama `app-release.aab`. Folder ini diabaikan Git.
7. Unggah `.aab` release yang ditandatangani, bukan APK debug.

Proyek belum mempunyai `signingConfig` release. Perintah `./gradlew bundleRelease` dapat digunakan untuk memeriksa build bundle, tetapi hasilnya **belum ditandatangani** dan belum siap diunggah. Wizard Android Studio di atas menyelesaikan signing tanpa menyimpan password di source. Lihat [panduan resmi penandatanganan aplikasi](https://developer.android.com/studio/publish/app-signing).

## 5. Ganti nama dan aset di listing Google Play

Nama serta ikon di source **tidak otomatis mengganti listing Play Store**. Ikon Play Store juga terpisah dari ikon launcher Android. Lihat [aset pratinjau Google Play](https://support.google.com/googleplay/android-developer/answer/9866151).

1. Buka **Grow users → Store presence → Main store listing**. Pada sebagian tampilan, buka **Store listings**, lalu pilih listing utama.
2. Ubah **App name** menjadi **Kemokulator**. Periksa setiap bahasa/terjemahan yang aktif; judul listing dapat berbeda per bahasa.
3. Di **Graphics → App icon**, unggah `app/src/main/ic_launcher-playstore.png` dari proyek ini. Berkas sudah disiapkan sebagai PNG RGBA 32-bit, sRGB, 512 × 512 px, di bawah 1024 KB, dengan latar putih penuh. Jangan menambahkan sudut membulat atau bayangan luar: Google Play akan menerapkannya. Lihat [spesifikasi ikon Google Play](https://developer.android.com/distribute/google-play/resources/icon-design-specifications).
4. Periksa nama lama dalam **Short description**, **Full description**, teks pada screenshot, feature graphic, dan preview video. Ganti aset yang masih menampilkan nama/logo lama, termasuk screenshot halaman utama. Jika feature graphic perlu diganti, gunakan 1024 × 500 px, JPEG atau PNG 24-bit tanpa alpha.
5. Periksa juga **Custom store listings**, terjemahan, dan eksperimen listing yang masih aktif agar pengguna tidak menerima branding lama dari variasi lain.
6. Jika nama aplikasi disebut di halaman kebijakan privasi, situs dukungan, atau materi yang ditautkan listing, perbarui penyebutannya agar cocok. Pastikan URL yang dipakai tetap benar.
7. Simpan perubahan. Periksa **Policy and programmes / Policy and programs → App content** dan selesaikan isian yang memang diminta Console, termasuk deklarasi aplikasi kesehatan, Data safety, atau kebijakan privasi jika relevan. Isian harus menggambarkan perilaku aplikasi yang sebenarnya.

Judul **Kemokulator** berada di bawah batas 30 karakter untuk nama listing. Cara mengedit detail dan terjemahan tersedia di [panduan detail listing](https://support.google.com/googleplay/android-developer/answer/9859152).

## 6. Uji melalui Google Play

1. Buka **Test and release → Testing → Internal testing → Create new release**.
2. Unggah `.aab` bertanda tangan dari langkah 4 dan periksa hasil validasi package, versi, sertifikat, dan target API.
3. Isi nama rilis, misalnya **1.0.2 — Kemokulator**, serta catatan rilis sesuai perubahan yang benar-benar masuk dalam bundle. Contoh untuk perubahan branding ini:

   ```text
   <id>
   Nama aplikasi kini Kemokulator.
   Logo dan ikon aplikasi diperbarui.
   </id>
   ```

   Gunakan tag bahasa yang ditampilkan Console; bila editor menampilkan `id-ID`, ikuti tag tersebut. Jika bundle juga berisi perubahan lain sejak rilis Play sebelumnya, tambahkan catatannya setelah meninjau perubahan itu.

4. Simpan dan ikuti alur review/rollout yang ditampilkan Console. Atur daftar tester dan bagikan tautan opt-in kepada tester Anda. Pengaturan tester dijelaskan di [panduan internal testing](https://support.google.com/googleplay/android-developer/answer/9845334).
5. Pada perangkat tester, uji update dari versi Play sebelumnya tanpa uninstall. Pastikan ikon/nama baru muncul, aplikasi dapat dibuka, dan kalkulator bekerja. Jangan memasang APK debug di atas versi Play untuk menguji jalur update karena sertifikatnya berbeda.

Alur pembuatan rilis, pengujian, dan release notes: [menyiapkan dan meluncurkan rilis](https://support.google.com/googleplay/android-developer/answer/9859348).

## 7. Kirim pembaruan ke produksi

1. Setelah pengujian berhasil, buka **Test and release → Production**. Promosikan rilis yang sudah diuji jika tersedia, atau buat rilis dan pilih bundle yang sama melalui **Add from library**. Tidak perlu memakai kode versi baru untuk mempromosikan bundle yang sama ke track lain.
2. Periksa ringkasan rilis dan selesaikan error yang menghalangi publikasi. Periksa kembali catatan rilis, negara distribusi, serta persentase rollout yang diinginkan.
3. Buka **Publishing overview** dan pastikan perubahan rilis, judul, serta ikon listing tercantum. Kirim perubahan yang siap melalui **Send changes for review** atau tombol setara yang ditampilkan Console. Lihat [pengaturan review dan publikasi](https://support.google.com/googleplay/android-developer/answer/9859654).
4. Periksa pengaturan **Managed publishing**. Jika aktif, perubahan yang disetujui menunggu tindakan publikasi Anda; jika tidak aktif, perubahan dapat terbit setelah disetujui. Gunakan pengaturan ini bila perlu mengatur waktu penerbitan branding. Lihat [status dan proses publikasi](https://support.google.com/googleplay/android-developer/answer/9859751).
5. Setelah disetujui, selesaikan publikasi/rollout sesuai pengaturan tadi. Pantau status rilis dan laporan crash/ANR di Console. Naikkan persentase jika menggunakan staged rollout dan hasilnya baik. Lihat [panduan staged rollout](https://support.google.com/googleplay/android-developer/answer/6346149).

## 8. Periksa hasil yang sudah terbit

- Buka halaman Play Store aplikasi dan pastikan judul **Kemokulator**, ikon baru, deskripsi, dan screenshot sudah benar di setiap bahasa/listing aktif.
- Uji pembaruan melalui Play Store pada perangkat yang menerima rollout. Pastikan label launcher, ikon, dan halaman utama sudah memakai branding baru.
- Jika ikon lama masih tampak, pastikan versi terpasang sudah versi baru, lalu muat ulang launcher atau mulai ulang perangkat untuk menyegarkan cache. Hindari menghapus data aplikasi hanya untuk menyegarkan ikon.
- Simpan keystore secara aman, arsipkan `.aab` yang dirilis, dan catat `versionCode`, `versionName`, tanggal, serta track rilisnya.

Dokumen ini menyiapkan langkah manual di Play Console. Perubahan source dan hasil build lokal tidak dengan sendirinya mengunggah atau menerbitkan aplikasi.
