package com.example.myweather_.di

import android.content.Context
import com.example.myweather.data.remote.WeatherRemoteDataSource
import com.example.myweather_.data.WeatherRepositoryImpl
import com.example.myweather_.data.remote.WeatherApi
import com.example.myweather_.domain.WeatherRepository
import com.example.myweather_.presentation.WeatherViewModel
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
// FusedLocationProviderClient
    single {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }

    // Provide WeatherApiService (create your Ktor client here)
    single<WeatherApi> {
        WeatherRemoteDataSource()
    }

    // Provide Repository
    single<WeatherRepository> {
        WeatherRepositoryImpl(get())
    }
    viewModel { WeatherViewModel(get(), get(), get()) }

//    viewModel { (context: Context) ->
//        WeatherViewModel(
//            fusedClient = get(),
//            weatherRepository = get(),
//            context = context
//        )
//    }
    single { LocationServices.getFusedLocationProviderClient(get<Context>()) }

}
