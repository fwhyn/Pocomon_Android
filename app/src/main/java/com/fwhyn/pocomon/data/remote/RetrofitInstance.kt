package com.fwhyn.pocomon.data.remote

import com.fwhyn.pocomon.data.remote.pokemon.PokeApiInterface
import com.fwhyn.pocomon.data.utils.DataConstants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofitPokeApi by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val pokeApi : PokeApiInterface by lazy{
        retrofitPokeApi.create(PokeApiInterface::class.java)
    }
}