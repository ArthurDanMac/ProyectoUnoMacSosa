package edu.pdm.proyectounomacsosa.model

data class LoginRequest(
    val token: String,
    val user: User
)