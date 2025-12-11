package edu.pdm.proyectounomacsosa.data.remote.apiclient

import edu.pdm.proyectounomacsosa.model.User
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApiService {
    @GET("/api/users/users")
    suspend fun getAllUsers(
        @Header("Authorization") token: String
    ): List<User>
}