package com.rsmbs.simrs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rsmbs.simrs.data.entity.Pasien
import kotlinx.coroutines.flow.Flow

@Dao
interface PasienDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pasien: Pasien)

    @Query("SELECT * FROM pasien WHERE nik = :nik LIMIT 1")
    suspend fun cariByNik(nik: String): Pasien?

    @Query("SELECT * FROM pasien WHERE noRekamMedis = :noRekamMedis LIMIT 1")
    suspend fun cariByNrm(noRekamMedis: String): Pasien?

    @Query("SELECT * FROM pasien ORDER BY namaPasien ASC")
    fun getAllPasien(): Flow<List<Pasien>>

    @Query("SELECT COUNT(*) FROM pasien")
    suspend fun countPasien(): Int
}
