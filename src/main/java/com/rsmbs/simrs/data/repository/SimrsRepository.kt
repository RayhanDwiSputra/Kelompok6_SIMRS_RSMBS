package com.rsmbs.simrs.data.repository

import androidx.room.withTransaction
import com.rsmbs.simrs.data.AppDatabase
import com.rsmbs.simrs.data.entity.AntrianItem
import com.rsmbs.simrs.data.entity.AsesmenMedis
import com.rsmbs.simrs.data.entity.DetailResep
import com.rsmbs.simrs.data.entity.EResep
import com.rsmbs.simrs.data.entity.Obat
import com.rsmbs.simrs.data.entity.Pasien
import com.rsmbs.simrs.data.entity.Pendaftaran
import com.rsmbs.simrs.data.entity.RekamMedis
import com.rsmbs.simrs.data.entity.ResepQueueItem
import com.rsmbs.simrs.data.entity.StatusAntrian
import com.rsmbs.simrs.data.entity.StatusBayar
import com.rsmbs.simrs.data.entity.StatusResep
import com.rsmbs.simrs.data.entity.Tagihan
import com.rsmbs.simrs.data.entity.TagihanQueueItem
import kotlinx.coroutines.flow.Flow

/** Item resep obat yang diinput dokter di layar Pemeriksaan, sebelum disimpan. */
data class ResepItemInput(
    val idObat: Long,
    val namaObat: String,
    val dosis: String,
    val jumlah: Int,
    val hargaSatuan: Long
)

/**
 * Repository menjembatani seluruh DAO dan menjalankan proses lintas-modul
 * (mis. resep dokter -> antrian farmasi, pembayaran kasir -> izin serah obat)
 * sesuai alur pada Sequence Diagram SIMRS.
 */
class SimrsRepository(private val db: AppDatabase) {

    // ---------- Pendaftaran & Antrian (UC01, UC04) ----------

    suspend fun cariPasienByNik(nik: String): Pasien? = db.pasienDao().cariByNik(nik)

    suspend fun simpanPasienBaru(pasien: Pasien) = db.pasienDao().insert(pasien)

    fun getSemuaPasien(): Flow<List<Pasien>> = db.pasienDao().getAllPasien()

    suspend fun daftarKunjungan(noRekamMedis: String, poli: String, tanggal: String, noSEP: String?): Long {
        val nomorTerakhir = db.pendaftaranDao().getNomorAntrianTerakhir(poli, tanggal)
        val pendaftaran = Pendaftaran(
            noRekamMedis = noRekamMedis,
            tglKunjungan = tanggal,
            poliTujuan = poli,
            noAntrian = nomorTerakhir + 1,
            noSEP = noSEP,
            status = StatusAntrian.MENUNGGU
        )
        return db.pendaftaranDao().insert(pendaftaran)
    }

    fun getAntrianHariIni(tanggal: String): Flow<List<AntrianItem>> = db.pendaftaranDao().getAntrianHariIni(tanggal)

    suspend fun panggilAntrian(noRegistrasi: Long) =
        db.pendaftaranDao().updateStatus(noRegistrasi, StatusAntrian.DIPANGGIL)

    // ---------- Dokter & RME (UC05-UC09) ----------

    fun getAntrianDokter(tanggal: String): Flow<List<AntrianItem>> = db.pendaftaranDao().getAntrianDokter(tanggal)

    fun getObatList(): Flow<List<Obat>> = db.obatDao().getAllObat()

    /**
     * Menyimpan hasil pemeriksaan dokter secara transaksional:
     * asesmen (opsional) + rekam medis + resep + detail resep,
     * lalu memperbarui status kunjungan menjadi SELESAI_PERIKSA.
     */
    suspend fun simpanPemeriksaan(
        noRegistrasi: Long,
        tekananDarah: String,
        nadi: Int,
        suhuTubuh: Double,
        beratBadan: Double,
        keluhanUtama: String,
        kodeICD10: String,
        namaDiagnosa: String,
        anamnesis: String,
        rencanaTatalaksana: String,
        tglPeriksa: String,
        resepItems: List<ResepItemInput>
    ) {
        db.withTransaction {
            db.rekamMedisDao().insertAsesmen(
                AsesmenMedis(
                    noRegistrasi = noRegistrasi,
                    tekananDarah = tekananDarah,
                    nadi = nadi,
                    suhuTubuh = suhuTubuh,
                    beratBadan = beratBadan,
                    keluhanUtama = keluhanUtama
                )
            )
            val idRME = db.rekamMedisDao().insertRekamMedis(
                RekamMedis(
                    noRegistrasi = noRegistrasi,
                    kodeICD10 = kodeICD10,
                    namaDiagnosa = namaDiagnosa,
                    anamnesis = anamnesis,
                    rencanaTatalaksana = rencanaTatalaksana,
                    tglPeriksa = tglPeriksa
                )
            )
            if (resepItems.isNotEmpty()) {
                val idResep = db.resepDao().insertResep(
                    EResep(idRME = idRME, noRegistrasi = noRegistrasi, tglResep = tglPeriksa)
                )
                db.resepDao().insertDetail(
                    resepItems.map {
                        DetailResep(
                            idResep = idResep,
                            idObat = it.idObat,
                            namaObat = it.namaObat,
                            dosis = it.dosis,
                            jumlah = it.jumlah,
                            hargaSatuan = it.hargaSatuan
                        )
                    }
                )
            }
            db.pendaftaranDao().updateStatus(noRegistrasi, StatusAntrian.SELESAI_PERIKSA)
        }
    }

    // ---------- Farmasi (UC12, UC13) ----------

    fun getAntrianFarmasi(): Flow<List<ResepQueueItem>> = db.resepDao().getAntrianFarmasi()

    fun getDetailResep(idResep: Long): Flow<List<DetailResep>> = db.resepDao().getDetailFlow(idResep)

    /** Verifikasi & siapkan obat: kurangi stok gudang secara otomatis (FR-FM di NFSR). */
    suspend fun siapkanObat(idResep: Long) {
        db.withTransaction {
            val detail = db.resepDao().getDetail(idResep)
            detail.forEach { db.obatDao().kurangiStok(it.idObat, it.jumlah) }
            db.resepDao().updateStatus(idResep, StatusResep.DISIAPKAN)
        }
    }

    /**
     * Menyerahkan obat ke pasien. Hanya diizinkan jika tagihan kunjungan
     * berstatus LUNAS (FR-FM-02: obat hanya diserahkan setelah verifikasi kasir).
     */
    suspend fun serahkanObat(idResep: Long, noRegistrasi: Long): Result<Unit> {
        val tagihan = db.tagihanDao().getByRegistrasi(noRegistrasi)
        return if (tagihan?.statusBayar == StatusBayar.LUNAS) {
            db.resepDao().updateStatus(idResep, StatusResep.DISERAHKAN)
            db.pendaftaranDao().updateStatus(noRegistrasi, StatusAntrian.SELESAI)
            Result.success(Unit)
        } else {
            Result.failure(IllegalStateException("Pembayaran pasien belum lunas di Kasir."))
        }
    }

    // ---------- Kasir & Billing (UC14, UC15) ----------

    fun getAntrianKasir(tanggal: String): Flow<List<TagihanQueueItem>> = db.pendaftaranDao().getAntrianKasir(tanggal)

    suspend fun hitungTagihan(noRegistrasi: Long, jenisPenjamin: String): Tagihan {
        val biayaObat = db.resepDao().getTotalBiayaObat(noRegistrasi)
        val biayaJasaDokter = if (jenisPenjamin.equals("BPJS", ignoreCase = true)) 0L else 75_000L
        val total = biayaJasaDokter + biayaObat

        val existing = db.tagihanDao().getByRegistrasi(noRegistrasi)
        val tagihan = Tagihan(
            idTagihan = existing?.idTagihan ?: 0,
            noRegistrasi = noRegistrasi,
            biayaJasaDokter = biayaJasaDokter,
            biayaObat = biayaObat,
            totalBiaya = total,
            statusBayar = StatusBayar.BELUM_LUNAS
        )
        db.tagihanDao().insert(tagihan)
        return tagihan
    }

    fun getTagihanFlow(noRegistrasi: Long): Flow<Tagihan?> = db.tagihanDao().getByRegistrasiFlow(noRegistrasi)

    suspend fun prosesPembayaran(noRegistrasi: Long, metode: String, tanggal: String) {
        db.tagihanDao().bayar(noRegistrasi, StatusBayar.LUNAS, metode, tanggal)
    }

    // ---------- Seed data awal ----------

    suspend fun seedIfEmpty() {
        if (db.obatDao().countObat() == 0) {
            db.obatDao().insertAll(
                listOf(
                    Obat(namaObat = "Paracetamol 500mg", satuan = "Tablet", stok = 200, hargaSatuan = 500),
                    Obat(namaObat = "Amoxicillin 500mg", satuan = "Kapsul", stok = 150, hargaSatuan = 1200),
                    Obat(namaObat = "Ibuprofen 400mg", satuan = "Tablet", stok = 100, hargaSatuan = 800),
                    Obat(namaObat = "Cetirizine 10mg", satuan = "Tablet", stok = 80, hargaSatuan = 700),
                    Obat(namaObat = "Omeprazole 20mg", satuan = "Kapsul", stok = 90, hargaSatuan = 1500),
                    Obat(namaObat = "Vitamin C 500mg", satuan = "Tablet", stok = 250, hargaSatuan = 400),
                    Obat(namaObat = "Salbutamol Inhaler", satuan = "Botol", stok = 30, hargaSatuan = 35_000),
                    Obat(namaObat = "Cairan Infus RL 500ml", satuan = "Botol", stok = 60, hargaSatuan = 15_000)
                )
            )
        }
        if (db.pasienDao().countPasien() == 0) {
            db.pasienDao().insert(
                Pasien(
                    noRekamMedis = "RM-0001",
                    nik = "3273010101990001",
                    namaPasien = "Ahmad Fauzi",
                    tanggalLahir = "1990-01-01",
                    jenisKelamin = "L",
                    jenisPenjamin = "BPJS"
                )
            )
            db.pasienDao().insert(
                Pasien(
                    noRekamMedis = "RM-0002",
                    nik = "3273016006950002",
                    namaPasien = "Siti Nurhaliza",
                    tanggalLahir = "1995-06-20",
                    jenisKelamin = "P",
                    jenisPenjamin = "UMUM"
                )
            )
        }
    }
}
