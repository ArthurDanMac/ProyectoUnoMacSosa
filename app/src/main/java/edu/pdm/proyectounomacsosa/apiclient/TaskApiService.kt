package edu.pdm.proyectounomacsosa.apiclient

import edu.pdm.proyectounomacsosa.model.Task
import retrofit2.http.*


interface TaskApiService {

    @GET("/tasks")
    suspend fun getTasks(
        @Header("Authorization") token: String
    ): List<Task>

    @GET("/tasks/{id}")
    suspend fun getTaskById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Task

    @POST("/tasks")
    suspend fun createTask(
        @Header("Authorization") token: String,
        @Body task: Task
    ): Task

    @PUT("/tasks/{id}")
    suspend fun updateTask(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body task: Task
    )

    @DELETE("/tasks/{id}")
    suspend fun deleteTask(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    )

    @POST("/auth/login")
    suspend fun login(
        @Body user: User
    )


}