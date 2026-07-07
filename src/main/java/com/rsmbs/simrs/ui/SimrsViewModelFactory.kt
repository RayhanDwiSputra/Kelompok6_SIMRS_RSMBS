package com.rsmbs.simrs.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rsmbs.simrs.data.repository.SimrsRepository
import com.rsmbs.simrs.ui.dokter.DokterViewModel
import com.rsmbs.simrs.ui.farmasi.FarmasiViewModel
import com.rsmbs.simrs.ui.kasir.KasirViewModel
import com.rsmbs.simrs.ui.pendaftaran.PendaftaranViewModel

class SimrsViewModelFactory(private val repository: SimrsRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PendaftaranViewModel::class.java) ->
                PendaftaranViewModel(repository) as T
            modelClass.isAssignableFrom(DokterViewModel::class.java) ->
                DokterViewModel(repository) as T
            modelClass.isAssignableFrom(FarmasiViewModel::class.java) ->
                FarmasiViewModel(repository) as T
            modelClass.isAssignableFrom(KasirViewModel::class.java) ->
                KasirViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
