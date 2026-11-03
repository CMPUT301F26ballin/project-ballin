/*
 * Copyright 2023 The Android Open Source Project
 * Modifications copyright 2026 ballin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://apache.org
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// NOTICES OF MODIFICATION
// Modified by Nick Guimaraes on 10/26/26 to
// add compadibility to our app and make resuable for other screens

package com.example.project_ballin.reusableui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.*

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
