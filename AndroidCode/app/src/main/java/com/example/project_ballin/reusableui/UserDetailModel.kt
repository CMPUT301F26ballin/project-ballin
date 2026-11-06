package com.example.project_ballin.reusableui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontVariation.Settings
import androidx.lifecycle.ViewModel
import androidx.navigation.*
import androidx.navigation.compose.*
import androidx.room.util.copy
import com.example.project_ballin.Admin
import com.example.project_ballin.AdminList
import com.example.project_ballin.EntrantList
import com.example.project_ballin.OwnerList
import com.example.project_ballin.User
import com.example.project_ballin.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.owner.Owner
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class UserDetailModel: ViewModel() {

    private var _selectedRole = MutableStateFlow(UserRole.OWNER)
    val selectedRole = _selectedRole.asStateFlow()

    private var _uiState = MutableStateFlow<User>(Owner())
    var uiState = _uiState.asStateFlow()

    fun setDeviceId(deviceId: String){
        val current = _uiState.value
        _uiState.value = when (current) {
            is Entrant -> current.copy(deviceId = deviceId)
            is Owner -> current.copy(deviceId = deviceId)
            is Admin -> current.copy(deviceId = deviceId)
            else -> current
        }
    }

    fun onRoleChange(newRole: UserRole) {
        _selectedRole.value = newRole
        val current = _uiState.value

        _uiState.value = when (newRole) {
            UserRole.ENTRANT -> Entrant(
                firstName = current.firstName,
                lastName = current.lastName,
                phoneNum = current.phoneNum,
                email = current.email,
                profilePic = current.profilePic
            )
            UserRole.OWNER -> Owner(
                firstName = current.firstName,
                lastName = current.lastName,
                phoneNum = current.phoneNum,
                email = current.email,
                profilePic = current.profilePic
            )
            UserRole.ADMIN -> Admin(
                firstName = current.firstName,
                lastName = current.lastName,
                phoneNum = current.phoneNum,
                email = current.email,
                profilePic = current.profilePic
            )
        }
    }

    fun onFirstNameChange(newFirstName: String) {
        _uiState.update { current ->
            current.updateDetails(firstName = newFirstName)
        }
    }
    fun onLastNameChange(newLastName: String) {
        _uiState.update { current ->
            current.updateDetails(lastName = newLastName)
        }
    }
    fun onPhoneNumChange(newPhoneNum: String) {
        _uiState.update { current ->
            current.updateDetails(phoneNum = newPhoneNum)
        }
    }
    fun onEmailChange(newEmail: String) {
        _uiState.update { current ->
            current.updateDetails(email = newEmail)
        }
    }
    fun onProfilePicChange(newProfilePic: String) { //change once peter finds out how images work
        _uiState.update { current ->
            current.updateDetails(profilePic = newProfilePic)
        }
    }

    fun onEdit() {
        _uiState.value.editProfile()
    }

    fun onCreate(){
        _uiState.value.createProfile()
    }

    fun onDelete(){
        _uiState.value.deleteProfile()
    }
     fun validateDetails(): Boolean {
        val currentUser = _uiState.value

         val isValidFirstName = currentUser.firstName.isNotBlank()
         val isValidLastName = currentUser.lastName.isNotBlank()
         val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(currentUser.email).matches()
         val isPhoneValid = android.util.Patterns.PHONE.matcher(currentUser.phoneNum).matches()

         return isValidFirstName && isValidLastName && isEmailValid && isPhoneValid
    }
}


