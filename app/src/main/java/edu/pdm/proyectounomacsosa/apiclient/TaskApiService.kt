package edu.pdm.proyectounomacsosa.apiclient

import edu.pdm.proyectounomacsosa.model.Task
import retrofit2.http.*


interface TaskApiService {

    @GET(".")
    suspend fun getTasks(
        @Header("Authorization") token: String
    ): List<Task>

    @GET("{id}")
    suspend fun getTaskById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Task

    @POST(".")
    suspend fun createTask(
        @Header("Authorization") token: String,
        @Body task: Task
    ): Task

    @PUT("{id}")
    suspend fun updateTask(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body task: Task
    )

    @DELETE("{id}")
    suspend fun deleteTask(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    )


}