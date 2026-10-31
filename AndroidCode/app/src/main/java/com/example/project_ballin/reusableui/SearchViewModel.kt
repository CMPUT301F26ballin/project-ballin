package com.example.project_ballin.reusableui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.project_ballin.Campsite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewmodel.compose.viewModel

class SearchViewModel: ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _filters = mutableStateListOf<Campsite>()
    val filters: List<Campsite> = _filters

    private val _campsites = MutableStateFlow(listOf<Campsite>())
    val campsites = query
        .combine(_campsites) {
            text, campsites ->
            if (text.isBlank()) {
                campsites
            }else{
                campsites.filter { campsite ->
                    campsite.name.contains(text, ignoreCase = true)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _campsites.value
        )


    fun onSearchTextChange(text: String) {
        _query.value = text
    }

}

