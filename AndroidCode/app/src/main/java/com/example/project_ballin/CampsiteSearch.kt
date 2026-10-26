package com.example.project_ballin

/**
 *
 * A class that searches over the campsite list based
 * on a query and filters to return a list of results
 *
 */
class CampsiteSearch {
    val filteredCampsiteList = ArrayList<Campsite>()

    fun search(campsiteList: List<Campsite>, query: String, filters: ArrayList<String>) {
        filteredCampsiteList.clear()
        for (campsite in campsiteList) {
            if (campsite.name.contains(query, ignoreCase = true)) {
                if(filter(campsite, filters)){
                    filteredCampsiteList.add(campsite)
                }
            }
        }
    }

    fun filter(campsite: Campsite, filters: ArrayList<String>): Boolean {
        if (filters.isEmpty()) return true

        var satisfyFilters = 0

        for (filter in filters) {
            if (campsite.tags.contains(filter)) {
                satisfyFilters++
            }
        }

        return satisfyFilters == filters.size
    }
}



