package edu.pdm.proyectounomacsosa.apiclient

import edu.pdm.proyectounomacsosa.model.Task
import retrofit2.http.*


interface TaskApiService {

    @GET(".")
    suspend fun getTasks(
        @Header("Authorization") token: String
    ): List<Task>

    @POST(".")
    suspend fun createTask(
        @Header("Authorization") token: String,
        @Body task: Task
    ): Task
}