package com.twugteam.admin.notemark.core.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import com.twugteam.admin.notemark.core.domain.network.ConnectivityObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber


class NetworkConnectivityObserver(
    context: Context
) : ConnectivityObserver {

    private val connectivityManager = context.getSystemService<ConnectivityManager>()!!

    override val isConnected: Flow<Boolean>
        get() = callbackFlow {
            val callback = object : NetworkCallback(){
                override fun onAvailable(network: Network) {

                    super.onAvailable(network)
                    Timber.tag("NetworkConnectivity").d("onAvailable")
                    trySend(true)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Timber.tag("NetworkConnectivity").d("onLost")
                    trySend(false)
                }

                //not used in registerNetwork only in requestNetwork()
                override fun onUnavailable() {
                    super.onUnavailable()
                    Timber.tag("NetworkConnectivity").d("onUnavailable")
                    trySend(false)
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    Timber.tag("NetworkConnectivity").d("onCapabilitiesChanged")
                    //if there is internet and not only connected to wifi/mobile data without internet
                    val connected = networkCapabilities.hasCapability(
                        NetworkCapabilities.NET_CAPABILITY_VALIDATED
                    )

                    trySend(connected)
                }
            }
            //register callbackFlow
            connectivityManager.registerDefaultNetworkCallback(callback)

            //when flow cancelled it will run this awaitClose
            //flow will get cancelled when it's scope is destroyed
            //example: in viewmodel viewmodelScope.launch, when viewmodel destroyed awaitClose will run
            awaitClose{
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }
}