package com.muco.squatdepthassist.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import timber.log.Timber

/**
 * This Extension is for catch the exception when the new destination is the same of the current direction.
 * It is not necessary to notify, because if there is this error, then there is already an action to the same destination
 */
fun NavController.safeNavigate(directions: NavDirections) {

    try {
        navigate(directions)
    } catch (e : IllegalArgumentException) {
        // Sometimes navigation destinations seem not to be found. An IllegalArgumentException like:
        // `navigation destination [...] is unknown to this NavController`
        // is thrown. This method is meant to overcome the issue.
        Timber.d(e)
    }
}