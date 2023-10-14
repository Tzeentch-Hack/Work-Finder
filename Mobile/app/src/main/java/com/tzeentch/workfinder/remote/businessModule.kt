package com.tzeentch.workfinder.remote

import com.tzeentch.workfinder.local.PreferenceManager
import com.tzeentch.workfinder.local.PreferenceManagerImpl
import com.tzeentch.workfinder.repositories.MainRepository
import com.tzeentch.workfinder.viewModels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

fun businessModule(): Module = module {
    single<PreferenceManager> { PreferenceManagerImpl(get()) }
    single { MainRepository(get()) }
    viewModelOf(::MainViewModel)
}