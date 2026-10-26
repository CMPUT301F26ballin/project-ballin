package com.example.project_ballin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.project_ballin.ui.theme.BallinSecondaryGreen
import com.example.project_ballin.ui.theme.BallinWhite

/**
 *
 * once the user logs in, from the homescreen onwards,
 * this function should be called. it displays the back button and profile button
 * use like this:
 * TopAppBar(navController){
 *     YourUIElements()
 *}
 * the buttons are True by default disable them by setting param to false.
 *
 * @param navController a [NavController] for the back button.
 * @param screenTitle appropiate title for screen user, auto capitalizes.
 * @param backButton  show or don't show the back button.
 * @param profileButton show or don't show the profile button.
 * @param content to place UI content below the app bar.
 *
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavController,
              screenTitle: String,
              backButton: Boolean = true,
              profileButton: Boolean = true,
              content: @Composable BoxScope.() -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults
        .pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = screenTitle.replaceFirstChar { it.titlecase() },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    if (backButton) { //make false on home screens
                        Button(
                            onClick = {
                                navController.popBackStack()
                            },
                            modifier = Modifier.testTag("back") // for testing
                        ) {
                            Text("Back")
                        }
                    }
                },
                actions = {
                    if (profileButton) { //make false on profile screens
                        Button(
                            onClick = {
                                navController.navigate("profile")
                            },
                            modifier = Modifier.testTag("profile") // for testing
                        ) {
                            Text("Profile")
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    val navController = rememberNavController()
    TopAppBar(navController, "test")
}
