package edu.pdm.proyectounomacsosa.apiclient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient {
    private const val BASE_URL = "https://api-android-eight.vercel.app/"

    val api: TaskApiService by lazy {
        Retrofit.Builder()
            .baseUrl(edu.pdm.proyectounomacsosa.apiclient.RetrofitClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaskApiService::class.java)
    }
}