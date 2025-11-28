package edu.pdm.proyectounomacsosa.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun task_dao(): TaskDao
}