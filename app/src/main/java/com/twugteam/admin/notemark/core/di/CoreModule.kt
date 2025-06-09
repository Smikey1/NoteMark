package com.twugteam.admin.notemark.core.di

import com.twugteam.admin.notemark.core.data.auth.EncryptedSessionStorage
import com.twugteam.admin.notemark.core.data.networking.HttpClientFactory
import com.twugteam.admin.notemark.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val coreModule = module {

    single {
        HttpClientFactory(get()).build()
    }

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}