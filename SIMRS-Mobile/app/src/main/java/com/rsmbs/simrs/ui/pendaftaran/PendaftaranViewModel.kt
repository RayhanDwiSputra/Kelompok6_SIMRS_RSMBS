package com.rsmbs.simrs.ui.pendaftaran

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsmbs.simrs.data.entity.AntrianItem
import com.rsmbs.simrs.data.entity.Pasien
import com.rsmbs.simrs.data.repository.SimrsRepository
import com.rsmbs.simrs.util.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

val DAFTAR_POLI = listOf("Poli Umum", "Poli Anak", "Poli Gigi", "Poli Kebidanan", "Poli Penyakit Dalam")

data class PendaftaranUiState(
    val pasienDitemukan: Pasien? = null,
    val sudahDicari: Boolean = false,
    val isLoading: Boolean = false,
    val pesan: String? = null,
    val berhasilDaftar: Boolean = false
)

class PendaftaranViewModel(private val repository: SimrsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(PendaftaranUiState())
    val uiState: StateFlow<PendaftaranUiState> = _uiState

    val antrianHariIni: StateFlow<List<AntrianItem>> = repository.getAntrianHariIni(DateUtils.today())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** Daftar seluruh pasien yang sudah pernah terdaftar, untuk fitur quick-register kunjungan baru. */
    val semuaPasien: StateFlow<List<Pasien>> = repository.getSemuaPasien()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun cariPasien(nik: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val pasien = repository.cariPasienByNik(nik)
            _uiState.value = _uiState.value.copy(
                pasienDitemukan = pasien,
                sudahDicari = true,
                isLoading = false,
                pesan = if (pasien == null) "Pasien belum terdaftar. Silakan isi data pasien baru." else null
            )
        }
    }

    /** Langsung tampilkan form pasien baru tanpa perlu mencari NIK terlebih dahulu. */
    fun tampilkanFormPasienBaru() {
        _uiState.value = PendaftaranUiState(sudahDicari = true, pasienDitemukan = null, pesan = null)
    }

    fun resetPencarian() {
        _uiState.value = PendaftaranUiState()
    }

    fun daftarPasienBaruDanDaftarkanKunjungan(
        nik: String,
        nama: String,
        tanggalLahir: String,
        jenisKelamin: String,
        jenisPenjamin: String,
        poli: String,
        noSEP: String?
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val noRekamMedis = "RM-" + System.currentTimeMillis().toString().takeLast(8)
            repository.simpanPasienBaru(
                Pasien(
                    noRekamMedis = noRekamMedis,
                    nik = nik,
                    namaPasien = nama,
                    tanggalLahir = tanggalLahir,
                    jenisKelamin = jenisKelamin,
                    jenisPenjamin = jenisPenjamin
                )
            )
            repository.daftarKunjungan(noRekamMedis, poli, DateUtils.today(), noSEP)
            _uiState.value = PendaftaranUiState(berhasilDaftar = true, pesan = "Pendaftaran berhasil. Nomor antrian telah diterbitkan.")
        }
    }

    fun daftarkanKunjunganPasienLama(noRekamMedis: String, poli: String, noSEP: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            repository.daftarKunjungan(noRekamMedis, poli, DateUtils.today(), noSEP)
            _uiState.value = PendaftaranUiState(berhasilDaftar = true, pesan = "Pendaftaran berhasil. Nomor antrian telah diterbitkan.")
        }
    }

    fun panggilAntrian(noRegistrasi: Long) {
        viewModelScope.launch { repository.panggilAntrian(noRegistrasi) }
    }
}

