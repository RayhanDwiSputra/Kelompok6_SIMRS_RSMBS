package com.rsmbs.simrs.ui.farmasi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsmbs.simrs.data.entity.DetailResep
import com.rsmbs.simrs.data.entity.ResepQueueItem
import com.rsmbs.simrs.data.repository.SimrsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class FarmasiUiState(
    val pesanError: String? = null,
    val pesanSukses: String? = null
)

class FarmasiViewModel(private val repository: SimrsRepository) : ViewModel() {

    val antrianFarmasi: StateFlow<List<ResepQueueItem>> = repository.getAntrianFarmasi()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _uiState = MutableStateFlow(FarmasiUiState())
    val uiState: StateFlow<FarmasiUiState> = _uiState

    fun getDetailResep(idResep: Long): StateFlow<List<DetailResep>> =
        repository.getDetailResep(idResep).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun siapkanObat(idResep: Long) {
        viewModelScope.launch {
            repository.siapkanObat(idResep)
            _uiState.value = FarmasiUiState(pesanSukses = "Obat berhasil disiapkan & stok gudang diperbarui.")
        }
    }

    fun serahkanObat(idResep: Long, noRegistrasi: Long) {
        viewModelScope.launch {
            val result = repository.serahkanObat(idResep, noRegistrasi)
            _uiState.value = if (result.isSuccess) {
                FarmasiUiState(pesanSukses = "Obat berhasil diserahkan ke pasien.")
            } else {
                FarmasiUiState(pesanError = result.exceptionOrNull()?.message ?: "Gagal menyerahkan obat.")
            }
        }
    }

    fun clearPesan() {
        _uiState.value = FarmasiUiState()
    }
}
