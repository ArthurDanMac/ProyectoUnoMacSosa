package edu.pdm.proyectounomacsosa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.model.User

@Database(entities = [Task::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun task_dao(): TaskDao
}