package com.gianlucaveschi.linkedinmock.domain.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkStateHelper {
    companion object{
        //Test Network State
        fun isNetworkConnected(connectivityManager : ConnectivityManager): Boolean {
            // Query the state of network.
            // val connectivityManager = Context.getSystemService(connectivityService) as ConnectivityManager
            // Reference to the active network used by the device
            val activeNetwork = connectivityManager.activeNetwork
            // Get the capabilities of the active network
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            // Check if the active network can reach the internet
            return networkCapabilities != null && networkCapabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
    }
}