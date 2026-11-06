package com.example.project_ballin.entrant.entantui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.project_ballin.Campsite
import com.example.project_ballin.entrant.Entrant
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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

    private val _db = Firebase.firestore
    private val _campsiteRef = _db.collection("Campsites")
    private val _campsites = MutableStateFlow<List<Campsite>>(emptyList())

    init {
        _campsiteRef.addSnapshotListener { snapshots, error ->
            if(error != null){
                return@addSnapshotListener
            }

            val fetchedSites = mutableListOf<Campsite>()
            snapshots?.documents?.forEach { document ->
                val campsite = document.toObject(Campsite::class.java)
                if (campsite != null) {
                    fetchedSites.add(campsite)
                }
            }
            _campsites.value = fetchedSites
        }
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
            _campsites.value
        )
}