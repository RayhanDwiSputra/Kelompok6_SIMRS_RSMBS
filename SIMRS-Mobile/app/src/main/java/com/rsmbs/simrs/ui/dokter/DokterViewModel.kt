package com.rsmbs.simrs.ui.dokter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsmbs.simrs.data.entity.AntrianItem
import com.rsmbs.simrs.data.entity.Obat
import com.rsmbs.simrs.data.repository.ResepItemInput
import com.rsmbs.simrs.data.repository.SimrsRepository
import com.rsmbs.simrs.util.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class PemeriksaanUiState(
    val isSaving: Boolean = false,
    val berhasilSimpan: Boolean = false,
    val errorMessage: String? = null,
    val resepItems: List<ResepItemInput> = emptyList()
)

class DokterViewModel(private val repository: SimrsRepository) : ViewModel() {

    val antrianDokter: StateFlow<List<AntrianItem>> = repository.getAntrianDokter(DateUtils.today())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val daftarObat: StateFlow<List<Obat>> = repository.getObatList()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _uiState = MutableStateFlow(PemeriksaanUiState())
    val uiState: StateFlow<PemeriksaanUiState> = _uiState

    fun tambahResepItem(obat: Obat, dosis: String, jumlah: Int) {
        val item = ResepItemInput(
            idObat = obat.idObat,
            namaObat = obat.namaObat,
            dosis = dosis,
            jumlah = jumlah,
            hargaSatuan = obat.hargaSatuan
        )
        _uiState.value = _uiState.value.copy(resepItems = _uiState.value.resepItems + item)
    }

    fun hapusResepItem(index: Int) {
        _uiState.value = _uiState.value.copy(
            resepItems = _uiState.value.resepItems.filterIndexed { i, _ -> i != index }
        )
    }

    fun simpanPemeriksaan(
        noRegistrasi: Long,
        tekananDarah: String,
        nadi: Int,
        suhuTubuh: Double,
        beratBadan: Double,
        keluhanUtama: String,
        kodeICD10: String,
        namaDiagnosa: String,
        anamnesis: String,
        rencanaTatalaksana: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true, errorMessage = null)
            try {
                repository.simpanPemeriksaan(
                    noRegistrasi = noRegistrasi,
                    tekananDarah = tekananDarah,
                    nadi = nadi,
                    suhuTubuh = suhuTubuh,
                    beratBadan = beratBadan,
                    keluhanUtama = keluhanUtama,
                    kodeICD10 = kodeICD10,
                    namaDiagnosa = namaDiagnosa,
                    anamnesis = anamnesis,
                    rencanaTatalaksana = rencanaTatalaksana,
                    tglPeriksa = DateUtils.now(),
                    resepItems = _uiState.value.resepItems
                )
                _uiState.value = PemeriksaanUiState(berhasilSimpan = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isSaving = false, errorMessage = e.message)
            }
        }
    }

    fun resetForm() {
        _uiState.value = PemeriksaanUiState()
    }
}
