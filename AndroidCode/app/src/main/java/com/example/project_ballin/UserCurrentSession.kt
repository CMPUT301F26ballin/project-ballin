package com.example.project_ballin

import com.example.project_ballin.owner.Owner

/**
 * when the user signs in or logs in I need to access current ID,
 * (more so for admin people - to do a certain admin feature)
 */
class UserCurrentSession {
    var isAdmin = false
    var currentProfile = Owner()
    // I did owner right now because the composable for owner menu
    // needs the owner's userID to access it, change it later on
}