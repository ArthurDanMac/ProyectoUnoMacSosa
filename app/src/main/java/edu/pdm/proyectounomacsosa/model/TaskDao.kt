package edu.pdm.proyectounomacsosa.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(task: Task)

    @Query("SELECT *FROM task")
    suspend fun getAll():List<Task>?

    @Query("SELECT * FROM task WHERE id = :id LIMIT 1 ")
    suspend fun getById(id: Int): Task? // ? posiblidd de dar un resultado nulo

    @Query("SELECT * FROM task WHERE name like :name  ")
    suspend fun getByName(name: String): Task?
}