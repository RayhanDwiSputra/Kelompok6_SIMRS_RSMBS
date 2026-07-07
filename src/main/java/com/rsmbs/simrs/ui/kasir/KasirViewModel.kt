package com.rsmbs.simrs.ui.kasir

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsmbs.simrs.data.entity.Tagihan
import com.rsmbs.simrs.data.entity.TagihanQueueItem
import com.rsmbs.simrs.data.repository.SimrsRepository
import com.rsmbs.simrs.util.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class KasirDetailUiState(
    val tagihan: Tagihan? = null,
    val isProcessing: Boolean = false,
    val pembayaranBerhasil: Boolean = false
)

class KasirViewModel(private val repository: SimrsRepository) : ViewModel() {

    val antrianKasir: StateFlow<List<TagihanQueueItem>> = repository.getAntrianKasir(DateUtils.today())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _detailState = MutableStateFlow(KasirDetailUiState())
    val detailState: StateFlow<KasirDetailUiState> = _detailState

    fun hitungTagihan(noRegistrasi: Long, jenisPenjamin: String) {
        viewModelScope.launch {
            val tagihan = repository.hitungTagihan(noRegistrasi, jenisPenjamin)
            _detailState.value = KasirDetailUiState(tagihan = tagihan)
        }
    }

    fun prosesPembayaran(noRegistrasi: Long, metode: String) {
        viewModelScope.launch {
            _detailState.value = _detailState.value.copy(isProcessing = true)
            repository.prosesPembayaran(noRegistrasi, metode, DateUtils.now())
            _detailState.value = _detailState.value.copy(isProcessing = false, pembayaranBerhasil = true)
        }
    }

    fun resetDetail() {
        _detailState.value = KasirDetailUiState()
    }
}
