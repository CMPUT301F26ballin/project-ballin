package com.example.project_ballin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

/**
 * Admin screen listing all profiles, each with a Remove button.
 *
 * @param navController the app's navController
 * @param profiles the profiles to show
 * @param onDelete removes a profile
 */
@Composable
fun AdminProfileListScreen(
    navController: NavController,
    profiles: List<Profile>,
    onDelete: (Profile) -> Unit
) {
    TopAppBar(navController, "Profiles") {
        AdminProfileListView(
            profiles = profiles,
            onProfileClick = { profile ->
                navController.navigate("AdminProfileDetail/${profile.deviceId}")
            },
            onDelete = onDelete
        )
    }
}

/**
 * The profile list itself (no top bar), so it can be tested on its own.
 *
 * @param profiles the profiles to show
 * @param onProfileClick runs when a profile is tapped
 * @param onDelete runs after a remove is confirmed
 */
@Composable
fun AdminProfileListView(
    profiles: List<Profile>,
    onProfileClick: (Profile) -> Unit,
    onDelete: (Profile) -> Unit
) {
    // Profile waiting for delete confirmation (null = no pop-up)
    var profileToDelete by remember { mutableStateOf<Profile?>(null) }

    if (profiles.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("There are no profiles yet.", modifier = Modifier.testTag("noProfiles"))
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp).testTag("profileList"),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(profiles, key = { it.deviceId }) { profile ->
                ProfileItem(
                    profile = profile,
                    onClick = { onProfileClick(profile) },
                    onRemoveClick = { profileToDelete = profile }
                )
            }
        }
    }

    profileToDelete?.let { profile ->
        ConfirmDeleteDialog(
            itemDescription = "the profile of ${profile.name}",
            onConfirm = {
                onDelete(profile)
                profileToDelete = null
            },
            onCancel = { profileToDelete = null }
        )
    }
}

/**
 * One row in the profile list: name, role, email, and a Remove button.
 *
 * @param profile the profile to show
 * @param onClick runs when the row is tapped
 * @param onRemoveClick runs when Remove is pressed
 */
@Composable
fun ProfileItem(profile: Profile, onClick: () -> Unit, onRemoveClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("profileItem_${profile.deviceId}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(profile.name, fontSize = 18.sp)
                Text(profile.role, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                Text(profile.email, fontSize = 12.sp)
            }
            Button(
                onClick = onRemoveClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.testTag("removeProfile_${profile.deviceId}")
            ) {
                Text("Remove")
            }
        }
    }
}

/**
 * Admin screen showing one profile's full details, with a Remove button.
 *
 * @param navController the app's navController
 * @param profile the profile to show (null if it was deleted)
 * @param onDelete removes the profile
 */
@Composable
fun AdminProfileDetailScreen(
    navController: NavController,
    profile: Profile?,
    onDelete: (Profile) -> Unit
) {
    TopAppBar(navController, "Profile Details") {
        AdminProfileDetailView(
            profile = profile,
            onDelete = { deleted ->
                onDelete(deleted)
                navController.popBackStack() // back to the list
            }
        )
    }
}

/**
 * The profile details itself (no top bar), so it can be tested on its own.
 *
 * @param profile the profile to show (null if it was deleted)
 * @param onDelete runs after a remove is confirmed
 */
@Composable
fun AdminProfileDetailView(profile: Profile?, onDelete: (Profile) -> Unit) {
    if (profile == null) {
        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("This profile no longer exists.")
        }
        return
    }

    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // TODO: show profile picture
        ProfileDetailRow("Name", profile.name)
        ProfileDetailRow("Role", profile.role)
        ProfileDetailRow("Email", profile.email)
        ProfileDetailRow("Phone", profile.phoneNum.ifBlank { "Not provided" })
        ProfileDetailRow("Device ID", profile.deviceId)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth().testTag("removeProfileDetail")
        ) {
            Text("Remove Profile")
        }
    }

    if (showDialog) {
        ConfirmDeleteDialog(
            itemDescription = "the profile of ${profile.name}",
            onConfirm = {
                showDialog = false
                onDelete(profile)
            },
            onCancel = { showDialog = false }
        )
    }
}

/**
 * One labelled line of profile info, e.g. "Email" and the address.
 *
 * @param label the field name
 * @param value the field value
 */
@Composable
fun ProfileDetailRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
        Text(value, fontSize = 18.sp)
    }
}

private val sampleProfiles = listOf(
    Profile("device1", "John Ballin", "john@example.com", "780-555-0101", "", "Entrant"),
    Profile("device2", "Jane Keller", "jane@example.com", "", "", "Owner"),
    Profile("device3", "Brian Yu", "brian@example.com", "780-555-0199", "", "Entrant")
)

@Preview(showBackground = true)
@Composable
fun AdminProfileListScreenPreview() {
    val navController = rememberNavController()
    AdminProfileListScreen(navController, sampleProfiles, onDelete = {})
}

@Preview(showBackground = true)
@Composable
fun AdminProfileDetailScreenPreview() {
    val navController = rememberNavController()
    AdminProfileDetailScreen(navController, sampleProfiles[0], onDelete = {})
}
