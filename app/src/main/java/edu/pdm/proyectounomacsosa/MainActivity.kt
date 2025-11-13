package edu.pdm.proyectounomacsosa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import edu.pdm.proyectounomacsosa.model.AppDatabase
import edu.pdm.proyectounomacsosa.model.TaskRepository
import edu.pdm.proyectounomacsosa.ui.Navigator
import edu.pdm.proyectounomacsosa.ui.theme.ProyectoUnoMacSosaTheme
import edu.pdm.proyectounomacsosa.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Initialize database and repository
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "tasks.db"
        ).build()
        val repo = TaskRepository(db.task_dao())
        val viewModel = TaskViewModel(repo)
        //viewModel.login("root","password")
        viewModel.resolveDomain()
        setContent {
            // Use your custom dark theme
            ProyectoUnoMacSosaTheme(darkTheme = true) {
                val navController = rememberNavController()
                Navigator(viewModel = viewModel, navController = navController)
            }
        }
    }
}
