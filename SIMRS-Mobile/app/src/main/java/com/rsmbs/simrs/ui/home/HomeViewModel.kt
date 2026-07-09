package com.rsmbs.simrs.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsmbs.simrs.data.entity.AntrianItem
import com.rsmbs.simrs.data.repository.SimrsRepository
import com.rsmbs.simrs.util.DateUtils
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(repository: SimrsRepository) : ViewModel() {

    val antrianHariIni: StateFlow<List<AntrianItem>> = repository.getAntrianHariIni(DateUtils.today())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
