package com.example.project_ballin.reusableui

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.project_ballin.Admin
import com.example.project_ballin.User
import com.example.project_ballin.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.owner.Owner
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.util.UUID

/**
 *
 * view model to handle account creation, editing and deletion
 *
 * Sources:
 *
 * Profile Pictures:
 * https://firebase.google.com/docs/storage/android/upload-files?hl=en&authuser=0
 * https://www.youtube.com/watch?v=YgjYVbg1oiA&t=475s
 * https://medium.com/@enkosolutionsdeveloper/building-an-image-upload-app-using-kotlin-jetpack-compose-and-firebase-storage-bfa262c195ac
 *https://developer.android.com/develop/ui/compose/graphics/images/loading
 *
 */

class UserDetailModel: ViewModel() {
    private var _selectedRole = MutableStateFlow(UserRole.ENTRANT)
    val selectedRole = _selectedRole.asStateFlow()
    //----------------------------------------------------------------
    private var _uiState = MutableStateFlow<User>(Entrant())
    var uiState = _uiState.asStateFlow()
    //----------------------------------------------------------------
    private var _isloading = MutableStateFlow(true)
    var isLoading = _isloading.asStateFlow()
    //----------------------------------------------------------------
    private var _isUploadingImage = MutableStateFlow(false)
    val isUploadingImage = _isUploadingImage.asStateFlow()
    //----------------------------------------------------------------
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
        if (deviceId.isBlank()){
            _isloading.value = false
            return
        }
        val db = Firebase.firestore
        _isloading.value = true

        db.collection("Entrants").whereEqualTo("deviceId", deviceId).get()
            .addOnSuccessListener { entrantDocs ->
                if (!entrantDocs.isEmpty) {
                    val entrant = entrantDocs.documents[0].toObject(Entrant::class.java)
                    if (entrant != null) {
                        _uiState.value = entrant
                        _isloading.value = false
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
                                _isloading.value = false
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
                                        _isloading.value = false
                                        _selectedRole.value = UserRole.ADMIN
                                    }
                                }
                                _isloading.value = false
                            }

                    }
            }
    }
    fun uploadProfilePic(uri: Uri) {
        val current = _uiState.value
        val userId = current.userID.ifBlank { UUID.randomUUID().toString() }

        _isUploadingImage.value = true

        val fileRef = Firebase.storage.reference.child("profile_pics/$userId.jpg")
        fileRef.putFile(uri)
        .continueWithTask { task ->
                _isUploadingImage.value = false
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                fileRef.downloadUrl
            }
                .addOnSuccessListener { downloadUrl ->
                    _isUploadingImage.value = false
                    onProfilePicChange(downloadUrl.toString())
                }
                .addOnFailureListener { exception ->
                    _isUploadingImage.value = false
                    android.util.Log.e("UserDetailModel", "Upload failed: ${exception.message}", exception)
        }
    }

    fun removeProfilePic(){
        val current = _uiState.value
        val userId = current.userID.ifBlank { UUID.randomUUID().toString() }

        val fileRef = Firebase.storage.reference.child("profile_pics/$userId.jpg")

        fileRef.delete()
            .addOnSuccessListener {
                onProfilePicChange("")
            }
            .addOnFailureListener { exception ->
                android.util.Log.e("UserDetailModel", "Upload failed: ${exception.message}", exception)
            }
        onProfilePicChange("")
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
                profilePic = current.profilePic,
                deviceId = current.deviceId,
                userID = current.userID
            )
            UserRole.OWNER -> Owner(
                firstName = current.firstName,
                lastName = current.lastName,
                phoneNum = current.phoneNum,
                email = current.email,
                profilePic = current.profilePic,
                deviceId = current.deviceId,
                userID = current.userID
            )
            UserRole.ADMIN -> Admin(
                firstName = current.firstName,
                lastName = current.lastName,
                phoneNum = current.phoneNum,
                email = current.email,
                profilePic = current.profilePic,
                deviceId = current.deviceId,
                userID = current.userID
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
    fun onProfilePicChange(newProfilePic: String) {
        _uiState.update { current ->
            current.updateDetails(profilePic = newProfilePic)
        }
    }

    //----------------------------------------------------------------

    fun onDeleteProfilePic(){
        _uiState.update { current ->
            current.updateDetails(profilePic = "")
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
     * and email has som@something.com
     */
     fun validateDetails(): Boolean {
        val currentUser = _uiState.value

        val isValidFirstName = currentUser.firstName.isNotBlank()
        val isValidLastName = currentUser.lastName.isNotBlank()
        val isValidDeviceId = currentUser.deviceId.isNotBlank()
        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(currentUser.email).matches()
        val isPhoneValid =
            android.util.Patterns.PHONE.matcher(currentUser.phoneNum).matches()
                    && currentUser.phoneNum.length == 10

         return isValidFirstName && isValidLastName && isEmailValid && isPhoneValid && isValidDeviceId
    }
}


