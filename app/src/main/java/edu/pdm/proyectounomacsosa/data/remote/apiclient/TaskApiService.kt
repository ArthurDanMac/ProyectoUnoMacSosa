package edu.pdm.proyectounomacsosa.data.remote.apiclient

import edu.pdm.proyectounomacsosa.model.LoginRequest
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.model.User
import retrofit2.http.*


interface TaskApiService: Annotation{

    @GET("/api/tasks")
    suspend fun getTasks(
        @Header("Authorization") token: String,
        @Query("user_id") user_id: Int
    ): List<Task>

    @GET("/api/tasks/{id}")
    suspend fun getTaskById(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Path("user_id") user_id: Int
    ): Task

    @POST("/api/tasks")
    suspend fun createTask(
        @Header("Authorization") token: String,
        @Body task: Task
    ): Task

    @PUT("/api/tasks/{id}")
    suspend fun updateTask(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body task: Task
    )

    @DELETE("/api/tasks/{id}")
    suspend fun deleteTask(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    )


    @POST("/api/auth/login")
    suspend fun login(
        @Body u: User
    ): LoginRequest


}