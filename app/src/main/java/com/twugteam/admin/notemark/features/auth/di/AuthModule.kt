package com.twugteam.admin.notemark.features.auth.di

import com.twugteam.admin.notemark.features.auth.data.AuthRepositoryImpl
import com.twugteam.admin.notemark.features.auth.domain.EmailPatternValidator
import com.twugteam.admin.notemark.features.auth.domain.AuthRepository
import com.twugteam.admin.notemark.features.auth.domain.PatternValidator
import com.twugteam.admin.notemark.features.auth.domain.UserDataValidator
import com.twugteam.admin.notemark.features.auth.presentation.ui.landing.LandingScreenViewModel
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.LogInViewModel
import com.twugteam.admin.notemark.features.auth.presentation.ui.register.RegisterViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    single<PatternValidator> { EmailPatternValidator }
    singleOf(::UserDataValidator)
    viewModelOf(::LandingScreenViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LogInViewModel)

    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

}