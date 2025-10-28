package edu.pdm.proyectounomacsosa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavHostController
import androidx.room.Room
import edu.pdm.proyectounomacsosa.model.AppDatabase
import edu.pdm.proyectounomacsosa.model.TaskRepository
import edu.pdm.proyectounomacsosa.ui.DeleteScreen
import edu.pdm.proyectounomacsosa.ui.ListScreen
import edu.pdm.proyectounomacsosa.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "tasks.db").build()
        val repo = TaskRepository(db.task_dao())
        val viewModel = TaskViewModel(repo)
        setContent {
            MaterialTheme {
                DeleteScreen(
                    viewModel,
                    onSearch = { Unit  },
                    navController = NavHostController(applicationContext)
                )
            }
        }
    }
}
