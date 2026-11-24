package com.example.project_ballin.entrant.entantui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_ballin.Campsite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlin.uuid.Uuid

/**
 * A view model for the search bar. used to handle the query alongside the filters the user may
 * want to apply, filter by date still needs to be implemented but that requires daterange class
 * or whatever it is
 *
 */

class SearchViewModel: ViewModel() {
    //----------------------------------------------------------------
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun onSearchTextChange(text: String) {
        _query.value = text
    }
    //----------------------------------------------------------------
    private var _selectedNumOfGuests = MutableStateFlow<Int?>(null)
    var numOfGuests = _selectedNumOfGuests.asStateFlow()

    fun onNumOfGuestsChange(numFilter: String) {
        _selectedNumOfGuests.value = numFilter.toIntOrNull()
    }
    //----------------------------------------------------------------
    private var _selectedLocationFilter = MutableStateFlow<String?>(null)
    var locationFilter = _selectedLocationFilter.asStateFlow()

    fun onSelectedLocationFilterChange(locationFilter: String?){
        _selectedLocationFilter.value = locationFilter
    }
    //----------------------------------------------------------------
    private var _selectedDateFilter = MutableStateFlow<List<Long>?>(null)
    var dateFilter = _selectedDateFilter.asStateFlow()

    fun onSelectedDateFilterChange(dateFilter: List<Long>?){
        _selectedDateFilter.value = dateFilter
    }

    //----------------------------------------------------------------

    private val _campsites = MutableStateFlow<List<Campsite>>(emptyList())

    fun updateCampsites(newList: List<Campsite>) {
        _campsites.value = newList
    }

    val campsites = combine(
        query, numOfGuests, locationFilter, dateFilter, _campsites
    ) { text, guestLimit, locationLimit, dateLimit, campsites ->
        campsites.filter { campsite ->
            val matchesText = text.isBlank() ||
                    campsite.name.contains(text, ignoreCase = true)

            val matchesGuests = guestLimit == null ||
                    campsite.occupancy >= guestLimit

            val matchesLocation = locationLimit == null ||
                    campsite.locationName == locationLimit

            val dateLimit = dateLimit == null ||
                    campsite.availability.containsAll(dateLimit)


            matchesText && matchesGuests && matchesLocation && dateLimit
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}