package com.pedrofarnetani.spectacle.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDirections

internal fun NavController.navigateSafe(direction: NavDirections) {
    if (currentDestination?.getAction(direction.actionId) != null) navigate(direction)
}