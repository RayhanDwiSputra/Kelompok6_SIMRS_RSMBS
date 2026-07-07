package com.rsmbs.simrs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rsmbs.simrs.data.entity.Tagihan
import kotlinx.coroutines.flow.Flow

@Dao
interface TagihanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tagihan: Tagihan): Long

    @Query("SELECT * FROM tagihan WHERE noRegistrasi = :noRegistrasi LIMIT 1")
    suspend fun getByRegistrasi(noRegistrasi: Long): Tagihan?

    @Query("SELECT * FROM tagihan WHERE noRegistrasi = :noRegistrasi LIMIT 1")
    fun getByRegistrasiFlow(noRegistrasi: Long): Flow<Tagihan?>

    @Query(
        """
        UPDATE tagihan SET statusBayar = :status, metodeBayar = :metode, tglBayar = :tglBayar
        WHERE noRegistrasi = :noRegistrasi
        """
    )
    suspend fun bayar(noRegistrasi: Long, status: String, metode: String, tglBayar: String)
}
