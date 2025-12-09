package edu.pdm.proyectounomacsosa

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import edu.pdm.proyectounomacsosa.data.local.AppDatabase
import edu.pdm.proyectounomacsosa.data.network.NetworkMonitor
import edu.pdm.proyectounomacsosa.data.remote.RetrofitClient
import edu.pdm.proyectounomacsosa.data.remote.apiclient.TaskApiService
import edu.pdm.proyectounomacsosa.data.repository.TaskRepository
import edu.pdm.proyectounomacsosa.ui.Navigator
import edu.pdm.proyectounomacsosa.ui.theme.ProyectoUnoMacSosaTheme
import edu.pdm.proyectounomacsosa.ui.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Initialize database and repository
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "tasks.db"
        ).build()

        val repo = TaskRepository(
            dao = db.task_dao(),
            api = TaskApiService,
            networkMonitor = NetworkMonitor,
        )

        val viewModel = TaskViewModel(repo)

        setContent {
            // Use your custom dark theme
            ProyectoUnoMacSosaTheme(darkTheme = true) {
                val navController = rememberNavController()
                Navigator(viewModel = viewModel, navController = navController)
            }
        }
    }
}
