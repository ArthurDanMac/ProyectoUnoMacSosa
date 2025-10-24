package edu.pdm.proyectounomacsosa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.room.Room
import edu.pdm.proyectounomacsosa.model.AppDatabase
import edu.pdm.proyectounomacsosa.model.TaskRepository
import edu.pdm.proyectounomacsosa.ui.RegisterScreen
import edu.pdm.proyectounomacsosa.ui.theme.ProyectoUnoMacSosaTheme
import edu.pdm.proyectounomacsosa.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "users.db").build()
        val repo = TaskRepository(db.task_dao())
        val viewModel = TaskViewModel(repo)
        setContent {
            MaterialTheme {
                RegisterScreen(
                    viewModel,
                    onSearch = { Unit  },
                    navController = NavHostController(applicationContext)
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoUnoMacSosaTheme {
        Greeting("Android")
    }
}