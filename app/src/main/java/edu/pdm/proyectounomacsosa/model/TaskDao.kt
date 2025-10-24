package edu.pdm.proyectounomacsosa.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(task: Task) //insertar nueva task

    @Query("SELECT *FROM task")
    suspend fun getAll():List<Task>? //da todas las tareas

    @Query("SELECT * FROM task WHERE id = :id ")
    suspend fun getById(id: Int): Task? //regresa tarea por id

    @Query("SELECT * FROM task WHERE name like :name  ")
    suspend fun getByName(name: String): Task? //regresa tarea por nombre


    @Query("DELETE FROM task WHERE id = :id  ")
    suspend fun delete(id: Int): Int // elimina tarea por el id

}