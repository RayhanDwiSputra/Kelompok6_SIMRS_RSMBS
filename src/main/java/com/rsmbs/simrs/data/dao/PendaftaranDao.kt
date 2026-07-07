package com.rsmbs.simrs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rsmbs.simrs.data.entity.AntrianItem
import com.rsmbs.simrs.data.entity.Pendaftaran
import kotlinx.coroutines.flow.Flow

@Dao
interface PendaftaranDao {

    @Insert
    suspend fun insert(pendaftaran: Pendaftaran): Long

    @Update
    suspend fun update(pendaftaran: Pendaftaran)

    @Query("SELECT * FROM pendaftaran WHERE noRegistrasi = :noRegistrasi LIMIT 1")
    suspend fun getById(noRegistrasi: Long): Pendaftaran?

    @Query(
        """
        SELECT COALESCE(MAX(noAntrian), 0) FROM pendaftaran
        WHERE poliTujuan = :poli AND tglKunjungan = :tanggal
        """
    )
    suspend fun getNomorAntrianTerakhir(poli: String, tanggal: String): Int

    @Query("UPDATE pendaftaran SET status = :status WHERE noRegistrasi = :noRegistrasi")
    suspend fun updateStatus(noRegistrasi: Long, status: String)

    /** Antrian & pendaftaran hari ini (dipakai modul Pendaftaran & Antrian). */
    @Query(
        """
        SELECT p.noRegistrasi, p.noRekamMedis, ps.namaPasien, p.poliTujuan, p.noAntrian,
               p.status, ps.jenisPenjamin, p.tglKunjungan
        FROM pendaftaran p
        INNER JOIN pasien ps ON p.noRekamMedis = ps.noRekamMedis
        WHERE p.tglKunjungan = :tanggal
        ORDER BY p.noAntrian ASC
        """
    )
    fun getAntrianHariIni(tanggal: String): Flow<List<AntrianItem>>

    /** Antrian yang sudah dipanggil, menunggu diperiksa dokter. */
    @Query(
        """
        SELECT p.noRegistrasi, p.noRekamMedis, ps.namaPasien, p.poliTujuan, p.noAntrian,
               p.status, ps.jenisPenjamin, p.tglKunjungan
        FROM pendaftaran p
        INNER JOIN pasien ps ON p.noRekamMedis = ps.noRekamMedis
        WHERE p.tglKunjungan = :tanggal AND p.status = 'DIPANGGIL'
        ORDER BY p.noAntrian ASC
        """
    )
    fun getAntrianDokter(tanggal: String): Flow<List<AntrianItem>>

    /** Kunjungan yang sudah selesai diperiksa, siap ditagih di Kasir. */
    @Query(
        """
        SELECT p.noRegistrasi, ps.namaPasien, p.poliTujuan, ps.jenisPenjamin,
               p.status, t.statusBayar AS statusBayarTagihan
        FROM pendaftaran p
        INNER JOIN pasien ps ON p.noRekamMedis = ps.noRekamMedis
        LEFT JOIN tagihan t ON t.noRegistrasi = p.noRegistrasi
        WHERE p.tglKunjungan = :tanggal
          AND p.status = 'SELESAI_PERIKSA'
          AND (t.statusBayar IS NULL OR t.statusBayar != 'LUNAS')
        ORDER BY p.noAntrian ASC
        """
    )
    fun getAntrianKasir(tanggal: String): Flow<List<com.rsmbs.simrs.data.entity.TagihanQueueItem>>
}
