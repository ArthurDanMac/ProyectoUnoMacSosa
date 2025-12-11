package edu.pdm.proyectounomacsosa.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.pdm.proyectounomacsosa.model.User

@Dao
interface UserDAO {
    @Query("SELECT *FROM users")
    suspend fun getAll():List<User>? //da todas las tareas


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(user: List<User>) //Creo que todas las tareas se guardan en la API
}