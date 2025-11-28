package edu.pdm.proyectounomacsosa.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="users") //nombre de tabla

data class User(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val username: String,
    val password: String,
    val email: String
)
