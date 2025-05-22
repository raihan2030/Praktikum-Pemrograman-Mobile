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

class RiderListViewModel(private val title: String) : ViewModel() {

    private val _riderList = MutableStateFlow<List<KamenRider>>(emptyList())
    val riderList: StateFlow<List<KamenRider>> = _riderList

    private val _onClickRider = MutableStateFlow<KamenRider?>(null)
    val onClickRider: StateFlow<KamenRider?> = _onClickRider

    init {
        loadKamenRider()
    }

    private fun loadKamenRider() {
        viewModelScope.launch {
            val allRiders = KamenRiderRepository.getKamenRiderList()
            _riderList.value = allRiders
            Timber.tag("ListViewModel").i("Data item berhasil dimuat ke dalam list: ${allRiders.size} item")
        }
    }

    fun onRiderClick(rider: KamenRider) {
        _onClickRider.value = rider
    }

    fun clearRiderClick() {
        _onClickRider.value = null
    }
}

class RiderListViewModelFactory(private val title: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RiderListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RiderListViewModel(title) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
