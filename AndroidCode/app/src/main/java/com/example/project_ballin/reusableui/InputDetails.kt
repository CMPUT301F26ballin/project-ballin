package com.example.project_ballin.reusableui

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.project_ballin.ConfirmDeleteDialog
import com.example.project_ballin.UserRole
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.entrant.entantui.ConfirmActionDialog
import java.util.Locale

/**
 *
 * screen for the user (anyone) to enter their details in order to sign in and
 * use this to for the signing in process and edit profile
 *
 * Sources:
 *
 * Radio Buttons:
 * https://developer.android.com/develop/ui/compose/components/radio-button
 *
 * Profile Pics:
 * https://developer.android.com/develop/ui/compose/graphics/images/customize
 *
 *
 */

/**
 * lets the user choose on account creation whether they want ot be admin, entrant, owner
 */
@Composable
fun SelectProfileRole(
    viewModel: UserDetailModel
) {

    val selectedRole by viewModel.selectedRole.collectAsState()

    Text("Choose Account Type:")
    Spacer(modifier = Modifier.height(16.dp))
    Column(
        modifier = Modifier
            .selectableGroup()
    ) {
        UserRole.entries.forEach { role ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedRole == role,
                        onClick = { viewModel.onRoleChange(role) },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedRole == role,
                    onClick = null
                )
                Text(
                    text = role.toString().lowercase(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

/**
 * The delete profile button, will prompt action dialog confirming whether
 * the user wants to delete their profile
 * boots user back to sign up screen if acct is deleted
 *
 * @param navController the navigation controller for the app
 * @param viewModel the view model for the app
 *
 */

@Composable
fun DeleteProfile(
    navController: NavController,
    viewModel: UserDetailModel
){
    var showConfirmDelete by remember { mutableStateOf(false) }

    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error
        ),
        onClick = {
            showConfirmDelete = true
        },
        modifier = Modifier.fillMaxWidth()
    ){
        Text("Delete Profile")
    }
    if (showConfirmDelete) {
        showConfirmDelete.let {
            ConfirmActionDialog().ConfirmAction(
                "Are you sure you want to delete your Profile?" +
                        " This action cannot be undone",
                confirm = { confirmed ->
                    if (confirmed) {
                        viewModel.removeProfilePic()
                        viewModel.onDelete()
                        navController.navigate("signup")
                    }
                    showConfirmDelete = false
                }
            )
        }
    }
}

/**
 * ihe input detail screen, allows the user to input their details
 *
 * @param navController the navigation controller for the app
 * @param viewModel the view model for the app
 * @param mode the mode of the screen, create or edit
 *
 * still requires ability to recieve notifications
 *
 */
@SuppressLint("SuspiciousIndentation")
@Composable
fun InputDetails(
    navController: NavController,
    viewModel: UserDetailModel = viewModel(),
    mode: String
    ){

    val uiState by viewModel.uiState.collectAsState()
    val isUploadingImage by viewModel.isUploadingImage.collectAsState()

    val context = LocalContext.current
    val deviceId = remember {
        Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        ) ?: ""
    }

    LaunchedEffect(deviceId, mode) {
        if (mode == "create") {
            viewModel.setDeviceId(deviceId)
        } else {
            viewModel.loadExistingUser(deviceId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (mode == "create"){
            Text(
                text = "Before We Begin, Please Enter Your Details:",
                fontSize = 24.sp,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val imagePicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri: Uri? ->
            if (uri != null) {
                viewModel.uploadProfilePic(uri)
            }
        }

        if (isUploadingImage) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        } else if (uiState.profilePic.isBlank()) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .clip(CircleShape)
                    .clickable {
                        imagePicker.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Tap To Select Profile Picture",
                    textAlign = TextAlign.Center,
                    fontSize = 34.sp,
                    lineHeight = 34.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else{
            AsyncImage(
                model = uiState.profilePic,
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .clickable {
                        imagePicker.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.removeProfilePic()
                }
            ){
                Text("Delete Profile Picture")
            }

        }

        Spacer(modifier = Modifier.height(48.dp))
        if (mode == "create"){
            SelectProfileRole(viewModel)
        }

        OutlinedTextField(
            value = uiState.firstName,
            onValueChange = {viewModel.onFirstNameChange(it)},
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.lastName,
            onValueChange = {viewModel.onLastNameChange(it)},
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = {viewModel.onEmailChange(it)},
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)

        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.phoneNum,
            onValueChange = {viewModel.onPhoneNumChange(it)},
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)

        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Recieve Notifications")

        Switch(
            checked = true,
            onCheckedChange = { /*TODO*/ },
        )
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (mode == "edit"){
            DeleteProfile(
                navController,
                viewModel
            )
        }

        if (viewModel.validateDetails()) {
            Button(
                onClick = {
                    if (mode == "create"){
                        viewModel.onCreate()
                        when (viewModel.selectedRole.value) {
                            UserRole.ENTRANT -> {
                                navController.navigate("EntrantMenu/${uiState.userID}")
                            }
                            UserRole.OWNER -> {
                                navController.navigate("OwnerMenu/${uiState.userID}")
                            }
                            UserRole.ADMIN -> {
                                navController.navigate("AdminMenu/${uiState.userID}")
                            }
                        }

                    }else{
                        viewModel.onEdit()
                        when (viewModel.selectedRole.value) {
                            UserRole.ENTRANT -> {
                                navController.navigate("EntrantMenu/${uiState.userID}")
                            }
                            UserRole.OWNER -> {
                                navController.navigate("OwnerMenu/${uiState.userID}")
                            }
                            UserRole.ADMIN -> {
                                navController.navigate("AdminMenu/${uiState.userID}")
                            }
                        }

                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (mode == "create"){
                    Text("Create Account")
                }else{
                    Text("Save Changes")
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CreateProfileRolePreview() {
    val navController = rememberNavController()
    val mode = "create"
    InputDetails(navController, mode = mode)
}

@Preview(showBackground = true)
@Composable
fun EditProfileRolePreview() {
    val navController = rememberNavController()
    val mode = "edit"
    InputDetails(navController, mode = mode)

}