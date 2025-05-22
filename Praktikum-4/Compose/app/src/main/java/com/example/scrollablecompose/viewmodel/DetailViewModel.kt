package com.example.scrollablecompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scrollablecompose.model.KamenRider
import com.example.scrollablecompose.model.KamenRiderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailViewModel(private val id: Int) : ViewModel() {

    private val _rider = MutableStateFlow<KamenRider?>(null)
    val rider: StateFlow<KamenRider?> get() = _rider

    init {
        loadRiderById()
    }

    private fun loadRiderById() {
        viewModelScope.launch {
            val selected = KamenRiderRepository.getKamenRiderList().find { it.id == id }
            if (selected != null) {
                Timber.tag("DetailViewModel").i("Navigasi ke DetailScreen dengan data: ${selected.name}, tahun: ${selected.year}")
            } else {
                Timber.tag("DetailViewModel").w("Rider dengan ID $id tidak ditemukan")
            }
            _rider.value = selected
        }
    }
}

class DetailViewModelFactory(private val id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
