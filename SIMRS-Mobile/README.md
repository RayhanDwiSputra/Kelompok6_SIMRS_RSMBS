# SIMRS Mobile — RS Muhammadiyah Bandung Selatan

Aplikasi mobile prototipe SIMRS (Sistem Informasi Manajemen Rumah Sakit) untuk RS Muhammadiyah
Bandung Selatan, dibangun dengan **Kotlin + Jetpack Compose (Material 3)** dan **Room (SQLite)**
sebagai basis data lokal. Proyek ini merupakan implementasi dari hasil analisis OOAD (FSR/NFSR,
Use Case, Activity, Sequence, dan Class Diagram) yang telah disusun sebelumnya.

## Modul yang Tersedia

| Modul | Use Case Terkait | Fitur |
|---|---|---|
| **Pendaftaran & Antrian** | UC01, UC04 | Cari/daftar pasien (NIK/NRM), pilih poli, terbitkan nomor antrian otomatis, panggil antrian |
| **Rawat Jalan & RME** | UC05–UC09 | Lihat antrian dipanggil, input asesmen/vital sign, diagnosis ICD-10, buat e-resep multi-obat |
| **Farmasi** | UC12, UC13 | Antrian resep masuk, verifikasi & kurangi stok gudang otomatis, serahkan obat (terkunci sampai kasir konfirmasi lunas) |
| **Kasir & Billing** | UC14, UC15 | Hitung tagihan (jasa dokter + obat, otomatis Rp0 untuk jasa dokter BPJS), proses pembayaran multi-metode |

## Arsitektur

```
com.rsmbs.simrs
├── data/
│   ├── entity/         # Entity Room: Pasien, Pendaftaran, AsesmenMedis, RekamMedis, Obat, EResep, DetailResep, Tagihan
│   ├── dao/             # DAO Room per entity + query gabungan (JOIN) untuk antrian
│   ├── repository/      # SimrsRepository — logika bisnis lintas modul & transaksi
│   └── AppDatabase.kt    # Konfigurasi Room database (singleton)
├── ui/
│   ├── theme/            # Warna, tipografi, Material 3 theme
│   ├── navigation/       # NavHost & definisi rute (Jetpack Navigation Compose)
│   ├── common/            # Komponen UI reusable (TopBar, StatusChip, EmptyState, SectionCard)
│   ├── home/               # Menu utama (4 modul)
│   ├── pendaftaran/        # Registrasi pasien & antrian
│   ├── dokter/             # Antrian dokter & layar pemeriksaan (RME + resep)
│   ├── farmasi/            # Antrian & serah obat
│   └── kasir/              # Antrian kasir & detail tagihan/pembayaran
└── util/                  # DateUtils, CurrencyUtils
```

Pola arsitektur: **MVVM** — setiap layar punya `ViewModel` yang mengambil data dari
`SimrsRepository` sebagai `StateFlow`, tanpa dependency injection framework (Hilt) agar
proyek tetap ringan untuk tugas kuliah. `SimrsViewModelFactory` menyediakan semua ViewModel
secara manual dari `SimrsApplication`.

## Alur Bisnis Utama (sesuai Sequence Diagram)

1. **Petugas Administrasi** mendaftarkan pasien → sistem menerbitkan nomor antrian otomatis per poli/hari.
2. Petugas memanggil antrian → status berubah `MENUNGGU` → `DIPANGGIL`.
3. **Dokter** membuka antrian yang sudah dipanggil, input asesmen, diagnosis (ICD-10), dan resep obat multi-item.
   Menyimpan pemeriksaan otomatis meneruskan resep ke **Farmasi** dan mengubah status menjadi `SELESAI_PERIKSA`.
4. **Kasir** menghitung tagihan (jasa dokter + total harga obat) dan memproses pembayaran.
5. **Farmasi** hanya dapat menyerahkan obat setelah status pembayaran pasien tersebut **LUNAS**
   di Kasir — sesuai kebutuhan fungsional FR-FM-02.

## Cara Menjalankan

1. Buka folder ini di **Android Studio** (Hedgehog/Iguana ke atas direkomendasikan).
2. Tunggu Gradle sync selesai (dependensi akan diunduh otomatis: Compose BOM 2024.02.00, Room 2.6.1, Navigation Compose 2.7.7).
3. Jalankan pada emulator atau perangkat fisik dengan **minimum Android 8.0 (API 26)**.
4. Saat pertama kali dijalankan, aplikasi otomatis mengisi data awal (seed):
   - 8 obat contoh dengan stok & harga.
   - 2 pasien contoh: NIK `3273010101990001` (BPJS) dan `3273016006950002` (Umum).

## Alur Uji Coba Cepat (End-to-End)

1. Buka **Pendaftaran & Antrian** → cari NIK `3273010101990001` → pilih poli → **Daftarkan Kunjungan**.
2. Buka **Kelola Antrian Hari Ini** → tekan **Panggil Pasien**.
3. Buka **Rawat Jalan & RME** → pilih pasien tadi → isi vital sign & diagnosis → tambahkan 1–2 obat → **Simpan Pemeriksaan**.
4. Buka **Kasir & Billing** → pilih kunjungan tadi → cek rincian tagihan → **Proses Pembayaran**.
5. Buka **Farmasi** → **Verifikasi & Siapkan** → tombol **Serahkan Obat** kini aktif karena status sudah Lunas.

## Catatan Teknis

- Database SQLite dikelola sepenuhnya oleh **Room**, tersimpan lokal di perangkat (`simrs_rsmbs.db`).
- Belum ada autentikasi/login peran (role login) — navigasi modul dilakukan langsung dari menu utama
  untuk mempercepat demo; dapat dikembangkan lebih lanjut dengan modul Kelola User & Role (UC02).
- Modul Rawat Inap, Laboratorium/Radiologi, dan Pelaporan/Dashboard **belum termasuk** dalam versi ini
  sesuai cakupan yang disepakati (fokus: Pendaftaran, Rawat Jalan/RME, Farmasi, Kasir).
- Karena proyek dibuat di luar Android Studio, disarankan melakukan **Gradle Sync** ulang dan
  memeriksa versi Android Gradle Plugin/Kotlin bila terjadi konflik dengan versi Android Studio yang dipakai.
