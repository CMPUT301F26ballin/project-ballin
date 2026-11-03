package com.example.project_ballin.reusableui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.*

//@Composable
//fun FilterLocationChip(location: String) {
//    var selected by remember { mutableStateOf(false) }
//
//    FilterChip(
//        onClick = { selected = !selected },
//        label = {
//            Text(location)
//        },
//        selected = selected,
//        leadingIcon = if (selected) {
//            {
//                Icon(
//                    imageVector = Icons.Filled.Done,
//                    contentDescription = "Done icon",
//                    modifier = Modifier.size(FilterChipDefaults.IconSize)
//                )
//            }
//        } else {
//            null
//        },
//    )
//}