package com.twugteam.admin.notemark.core.domain.network

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    //return a live update about internet connection status
    val isConnected: Flow<Boolean>
    //return single variable and doesn't trigger any live change or connection status
    fun isCurrentlyConnected(): Boolean
}