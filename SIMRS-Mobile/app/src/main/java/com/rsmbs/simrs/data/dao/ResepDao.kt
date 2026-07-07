package com.rsmbs.simrs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rsmbs.simrs.data.entity.DetailResep
import com.rsmbs.simrs.data.entity.EResep
import com.rsmbs.simrs.data.entity.ResepQueueItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ResepDao {

    @Insert
    suspend fun insertResep(resep: EResep): Long

    @Insert
    suspend fun insertDetail(detail: List<DetailResep>)

    @Query("UPDATE e_resep SET statusObat = :status WHERE idResep = :idResep")
    suspend fun updateStatus(idResep: Long, status: String)

    @Query("SELECT * FROM detail_resep WHERE idResep = :idResep")
    suspend fun getDetail(idResep: Long): List<DetailResep>

    @Query("SELECT * FROM detail_resep WHERE idResep = :idResep")
    fun getDetailFlow(idResep: Long): Flow<List<DetailResep>>

    /** Antrian resep untuk modul Farmasi: yang belum selesai diserahkan. */
    @Query(
        """
        SELECT er.idResep, er.noRegistrasi, ps.namaPasien, p.poliTujuan, er.statusObat, er.tglResep,
               t.statusBayar
        FROM e_resep er
        INNER JOIN pendaftaran p ON er.noRegistrasi = p.noRegistrasi
        INNER JOIN pasien ps ON p.noRekamMedis = ps.noRekamMedis
        LEFT JOIN tagihan t ON t.noRegistrasi = p.noRegistrasi
        WHERE er.statusObat != 'DISERAHKAN'
        ORDER BY er.idResep ASC
        """
    )
    fun getAntrianFarmasi(): Flow<List<ResepQueueItem>>

    @Query("SELECT * FROM e_resep WHERE idResep = :idResep LIMIT 1")
    suspend fun getById(idResep: Long): EResep?

    /** Total biaya obat dari seluruh resep milik satu kunjungan (dipakai Kasir menghitung tagihan). */
    @Query(
        """
        SELECT COALESCE(SUM(dr.hargaSatuan * dr.jumlah), 0)
        FROM detail_resep dr
        INNER JOIN e_resep er ON dr.idResep = er.idResep
        WHERE er.noRegistrasi = :noRegistrasi
        """
    )
    suspend fun getTotalBiayaObat(noRegistrasi: Long): Long
}

