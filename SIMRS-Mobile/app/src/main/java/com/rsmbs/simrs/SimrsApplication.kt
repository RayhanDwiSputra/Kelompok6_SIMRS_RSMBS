package com.rsmbs.simrs

import android.app.Application
import android.util.Log
import com.rsmbs.simrs.data.AppDatabase
import com.rsmbs.simrs.data.repository.SimrsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class SimrsApplication : Application() {

    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
    val repository: SimrsRepository by lazy { SimrsRepository(database) }

    override fun onCreate() {
        super.onCreate()
        // Dijalankan blocking (bukan launch async) supaya data awal (pasien contoh +
        // obat contoh) dipastikan sudah tersimpan SEBELUM layar pertama ditampilkan.
        // Data seed sangat kecil sehingga aman untuk blocking sesaat di startup.
        runBlocking(Dispatchers.IO) {
            try {
                repository.seedIfEmpty()
                Log.d("SIMRS", "Seed data berhasil dipastikan tersedia.")
            } catch (e: Exception) {
                Log.e("SIMRS", "Gagal melakukan seeding data awal", e)
            }
        }
    }
}

