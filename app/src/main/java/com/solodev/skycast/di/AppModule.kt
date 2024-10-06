package com.solodev.skycast.di

import com.solodev.skycast.data.remote.WeatherApi
import com.solodev.skycast.data.repository.WeatherRepositoryImpl
import com.solodev.skycast.domain.repository.WeatherRepository
import com.solodev.skycast.domain.usecase.WeatherUseCase
import com.solodev.skycast.domain.usecase.GetWeather
import com.solodev.skycast.domain.usecase.GetWeatherForecast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder().baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataRepository(weatherApi: WeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi = weatherApi)
    }

    @Provides
    @Singleton
    fun provideMoviesUseCases(
        weatherRepository: WeatherRepository,
    ): WeatherUseCase {
        return WeatherUseCase(
            getWeather = GetWeather(weatherRepository),
            getWeatherForecast = GetWeatherForecast(weatherRepository)
        )
    }
}