package edu.pdm.proyectounomacsosa.data.remote

import edu.pdm.proyectounomacsosa.data.remote.apiclient.TaskApiService
import edu.pdm.proyectounomacsosa.data.remote.apiclient.UserApiService
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private const val BASE_URL = "https://api-android-eight.vercel.app/"

    val Api: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    val apiTask: TaskApiService = Api.create(TaskApiService::class.java)
    val apiUser: UserApiService = Api.create(UserApiService::class.java)



}