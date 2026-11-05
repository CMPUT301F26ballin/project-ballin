package com.example.project_ballin.entrant.entantui

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
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun onSearchTextChange(text: String) {
        _query.value = text
    }

    private var _selectedNumOfGuests = MutableStateFlow<Int?>(null)
    var numOfGuests = _selectedNumOfGuests.asStateFlow()

    fun onNumOfGuestsChange(dateFilter: String) {
        _selectedNumOfGuests.value = dateFilter.toIntOrNull()
    }
    private var _selectedLocationFilter = MutableStateFlow<String?>(null)
    var locationFilter = _selectedLocationFilter.asStateFlow()

    fun onSelectedLocationFilterChange(locationFilter: String?){
        _selectedLocationFilter.value = locationFilter
    }

     val testSites: List<Campsite> = listOf( // replace with actual sites
         Campsite(
             name = "Pine Valley Campsite",
             ID = Uuid.random().toString(),
             locationName = "Banff National Park",
             tags = mutableListOf("Tent", "Scenic"),
             price = 35.0f,
             rating = 4.5f,
             rules = "No campfire after 10 PM",
             description = "Beautiful valley surrounded by pine trees.",
             occupancy = 4,
             ownerID = Uuid.random().toString(),
             images = mutableListOf()
         ),
         Campsite(
             name = "Lakeside Retreat",
             ID = Uuid.random().toString(),
             locationName = "Jasper",
             tags = mutableListOf("Lake", "Fishing"),
             price = 50.0f,
             rating = 4.8f,
             rules = "Catch and release only",
             description = "Right by the lake with amazing sunrise views.",
             occupancy = 6,
             ownerID = Uuid.random().toString(),
             images = mutableListOf()
         ),
         Campsite(
             name = "Mountain Peak Camp",
             ID = Uuid.random().toString(),
             locationName = "Rocky Mountains",
             tags = mutableListOf("Hiking", "Elevation"),
             price = 40.0f,
             rating = 4.2f,
             rules = "Pack it in, pack it out",
             description = "High altitude camping for experienced hikers.",
             occupancy = 2,
             ownerID = Uuid.random().toString(),
             images = mutableListOf()
         )
    ) //replace with actual sites
    private val _campsites = MutableStateFlow<List<Campsite>>(emptyList())

    fun updateCampsites(newList: List<Campsite>) {
        _campsites.value = newList
    }

    val campsites = combine(
        query, numOfGuests, locationFilter, _campsites
    ) { text, guestLimit, locationLimit, campsites ->
        campsites.filter { campsite ->
            val matchesText = text.isBlank() ||
                    campsite.name.contains(text, ignoreCase = true)

            val matchesGuests = guestLimit == null ||
                    campsite.occupancy >= guestLimit

            val matchesLocation = locationLimit == null ||
                    campsite.locationName == locationLimit

            matchesText && matchesGuests && matchesLocation
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}