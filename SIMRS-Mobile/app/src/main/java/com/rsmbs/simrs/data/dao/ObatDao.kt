package com.rsmbs.simrs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rsmbs.simrs.data.entity.Obat
import kotlinx.coroutines.flow.Flow

@Dao
interface ObatDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(obatList: List<Obat>)

    @Query("SELECT * FROM obat ORDER BY namaObat ASC")
    fun getAllObat(): Flow<List<Obat>>

    @Query("SELECT * FROM obat WHERE idObat = :idObat LIMIT 1")
    suspend fun getById(idObat: Long): Obat?

    @Query("SELECT COUNT(*) FROM obat")
    suspend fun countObat(): Int

    @Query("UPDATE obat SET stok = stok - :jumlah WHERE idObat = :idObat")
    suspend fun kurangiStok(idObat: Long, jumlah: Int)
}
