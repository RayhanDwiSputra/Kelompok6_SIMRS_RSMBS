package com.rsmbs.simrs.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rsmbs.simrs.data.dao.ObatDao
import com.rsmbs.simrs.data.dao.PasienDao
import com.rsmbs.simrs.data.dao.PendaftaranDao
import com.rsmbs.simrs.data.dao.RekamMedisDao
import com.rsmbs.simrs.data.dao.ResepDao
import com.rsmbs.simrs.data.dao.TagihanDao
import com.rsmbs.simrs.data.entity.AsesmenMedis
import com.rsmbs.simrs.data.entity.DetailResep
import com.rsmbs.simrs.data.entity.EResep
import com.rsmbs.simrs.data.entity.Obat
import com.rsmbs.simrs.data.entity.Pasien
import com.rsmbs.simrs.data.entity.Pendaftaran
import com.rsmbs.simrs.data.entity.RekamMedis
import com.rsmbs.simrs.data.entity.Tagihan

@Database(
    entities = [
        Pasien::class,
        Pendaftaran::class,
        AsesmenMedis::class,
        RekamMedis::class,
        Obat::class,
        EResep::class,
        DetailResep::class,
        Tagihan::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pasienDao(): PasienDao
    abstract fun pendaftaranDao(): PendaftaranDao
    abstract fun rekamMedisDao(): RekamMedisDao
    abstract fun obatDao(): ObatDao
    abstract fun resepDao(): ResepDao
    abstract fun tagihanDao(): TagihanDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "simrs_rsmbs.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
