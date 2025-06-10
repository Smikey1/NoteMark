package com.twugteam.admin.notemark.features.auth.di

import com.twugteam.admin.notemark.features.auth.data.EmailPatternValidator
import com.twugteam.admin.notemark.features.auth.domain.PatternValidator
import com.twugteam.admin.notemark.features.auth.domain.UserDataValidator
import com.twugteam.admin.notemark.features.auth.presentation.landing.LandingScreenViewModel
import com.twugteam.admin.notemark.features.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    single<PatternValidator> { EmailPatternValidator }
    singleOf(::UserDataValidator)
    viewModelOf(::LandingScreenViewModel)
    viewModelOf(::RegisterViewModel)

}