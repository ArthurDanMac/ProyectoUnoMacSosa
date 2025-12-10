package edu.pdm.proyectounomacsosa.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.model.User

@Database(entities = [Task::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun task_dao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Retorna la instancia si ya existe; si no, la crea de forma segura.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tasks"
                ).build()
                INSTANCE = instance
                // retorna la instancia
                instance
            }
        }
    }
}