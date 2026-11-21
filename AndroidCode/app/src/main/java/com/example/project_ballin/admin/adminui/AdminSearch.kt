package com.example.project_ballin

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * The search view for Admin Users
 * */

class AdminSearch(){
    /**
     * The search box at the top of the admin's lists.
     *
     * @param query what the admin has typed
     * @param onQueryChange runs when the admin types or clears the box
     * @param placeholder the hint shown when the box is empty, e.g. "Search profiles"
     * @param modifier the modifier for the search box
     */
    @Composable
    fun AdminSearchField(
        query: String,
        onQueryChange: (String) -> Unit,
        placeholder: String,
        modifier: Modifier = Modifier
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text(placeholder) },
            singleLine = true,
            trailingIcon = {
                if (query.isNotEmpty()) {
                    TextButton(
                        onClick = { onQueryChange("") },
                        modifier = Modifier.testTag("clearSearch")
                    ) {
                        Text("Clear")
                    }
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("adminSearch")
        )
    }

    /**
     * Checks if any of the given fields contain the search text.
     * Ignores upper/lower case and spaces at the start or end of the search.
     * An empty search matches everything.
     *
     * @param query the search text
     * @param fields the fields to look in
     * @return true if the search text is in at least one field
     */
    fun matchesQuery(query: String, vararg fields: String): Boolean {
        val search = query.trim()
        if (search.isEmpty()) return true
        return fields.any { it.contains(search, ignoreCase = true) }
    }


    @Preview(showBackground = true)
    @Composable
    fun AdminSearchFieldPreview() {
        AdminSearchField(query = "Waterton", onQueryChange = {}, placeholder = "Search campsites")
    }
}
