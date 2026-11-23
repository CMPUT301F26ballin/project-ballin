package com.example.project_ballin.reusableui

import androidx.lifecycle.ViewModel
import com.example.project_ballin.Admin
import com.example.project_ballin.User
import com.example.project_ballin.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.owner.Owner
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

/**
 *
 * view model to handle account creation, editing and deletion
 *
 */

class UserDetailModel: ViewModel() {

    private var _selectedRole = MutableStateFlow(UserRole.ENTRANT)
    val selectedRole = _selectedRole.asStateFlow()

    private var _uiState = MutableStateFlow<User>(Entrant())
    var uiState = _uiState.asStateFlow()

    /**
     * sets the device ID of the user on account creation
     */
    fun setDeviceId(deviceId: String){
        val current = _uiState.value
        _uiState.value = when (current) {
            is Entrant -> current.copy(deviceId = deviceId)
            is Owner -> current.copy(deviceId = deviceId)
            is Admin -> current.copy(deviceId = deviceId)
            else -> current
        }
    }

    /**
     * loads an existing user from firebase based on their device ID
     */
    fun loadExistingUser(deviceId: String) {
        if (deviceId.isBlank()) return
        val db = Firebase.firestore

        db.collection("Entrants").whereEqualTo("deviceId", deviceId).get()
            .addOnSuccessListener { entrantDocs ->
                if (!entrantDocs.isEmpty) {
                    val entrant = entrantDocs.documents[0].toObject(Entrant::class.java)
                    if (entrant != null) {
                        _uiState.value = entrant
                        _selectedRole.value = UserRole.ENTRANT
                        return@addOnSuccessListener
                    }
                }

                db.collection("Owners").whereEqualTo("deviceId", deviceId).get()
                    .addOnSuccessListener { ownerDocs ->
                        if (!ownerDocs.isEmpty) {
                            val owner = ownerDocs.documents[0].toObject(Owner::class.java)
                            if (owner != null) {
                                _uiState.value = owner
                                _selectedRole.value = UserRole.OWNER
                                return@addOnSuccessListener
                            }
                        }

                        db.collection("Admins").whereEqualTo("deviceId", deviceId).get()
                            .addOnSuccessListener { adminDocs ->
                                if (!adminDocs.isEmpty) {
                                    val admin = adminDocs.documents[0].toObject(Admin::class.java)
                                    if (admin != null) {
                                        _uiState.value = admin
                                        _selectedRole.value = UserRole.ADMIN
                                    }
                                }
                            }
                    }
            }
    }

    /**
     * when creating an account, the user chooses which role they are
     *
     */
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

    /**
     * change the details of the user
     */
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
    fun onNotificationlist(){

    }

    /**
     * edit the details of the user
     */
    fun onEdit() {
        _uiState.value.editProfile()
    }

    /**
     * create the profile of the user
     */
    fun onCreate(){
        _uiState.value.createProfile()
    }

    /**
     * delete the profile of the user
     */
    fun onDelete(){
        _uiState.value.deleteProfile()
    }

    /**
     * used to validate the details of the user, ie. phone number is 10 digits long
     * and email has @something.com
     */
     fun validateDetails(): Boolean {
        val currentUser = _uiState.value

         val isValidFirstName = currentUser.firstName.isNotBlank()
         val isValidLastName = currentUser.lastName.isNotBlank()
         val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(currentUser.email).matches()
         val isPhoneValid =
             android.util.Patterns.PHONE.matcher(currentUser.phoneNum).matches()
                     && currentUser.phoneNum.length == 10

         return isValidFirstName && isValidLastName && isEmailValid && isPhoneValid
    }
}


