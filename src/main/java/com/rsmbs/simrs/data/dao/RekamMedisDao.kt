package com.rsmbs.simrs.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rsmbs.simrs.data.entity.AsesmenMedis
import com.rsmbs.simrs.data.entity.RekamMedis
import kotlinx.coroutines.flow.Flow

@Dao
interface RekamMedisDao {

    @Insert
    suspend fun insertAsesmen(asesmen: AsesmenMedis): Long

    @Insert
    suspend fun insertRekamMedis(rekamMedis: RekamMedis): Long

    @Query("SELECT * FROM asesmen_medis WHERE noRegistrasi = :noRegistrasi ORDER BY idAsesmen DESC LIMIT 1")
    suspend fun getAsesmenTerakhir(noRegistrasi: Long): AsesmenMedis?

    @Query("SELECT * FROM rekam_medis WHERE noRegistrasi = :noRegistrasi ORDER BY idRME DESC LIMIT 1")
    suspend fun getRekamMedisByRegistrasi(noRegistrasi: Long): RekamMedis?

    @Query(
        """
        SELECT rm.* FROM rekam_medis rm
        INNER JOIN pendaftaran p ON rm.noRegistrasi = p.noRegistrasi
        WHERE p.noRekamMedis = :noRekamMedis
        ORDER BY rm.idRME DESC
        """
    )
    fun getRiwayatRekamMedis(noRekamMedis: String): Flow<List<RekamMedis>>
}
