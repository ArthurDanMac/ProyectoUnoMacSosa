package edu.pdm.proyectounomacsosa.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.pdm.proyectounomacsosa.ui.screens.AddTaskScreen
import edu.pdm.proyectounomacsosa.ui.screens.DeleteScreen
import edu.pdm.proyectounomacsosa.ui.screens.ListByIdScreen
import edu.pdm.proyectounomacsosa.ui.screens.LogInScreen
import edu.pdm.proyectounomacsosa.ui.screens.TaskListScreen
import edu.pdm.proyectounomacsosa.ui.screens.UpdateScreen
import edu.pdm.proyectounomacsosa.ui.viewmodel.TaskViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigator(viewModel: TaskViewModel, navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("addTask") {
            AddTaskScreen(
                viewModel = viewModel,
                onSearch = { /* optional */ },
                navController = navController
            )
        }
        composable("seeTasks") {
            TaskListScreen(
                viewModel = viewModel,
                onSearch = { /* optional */ },
                navController = navController
            )
        }
        composable("listById") {
            ListByIdScreen(
                viewModel = viewModel,
                onSearch = { /* optional */ },
                navController = navController
            )
        }
        composable("delete") {
            DeleteScreen(
                viewModel = viewModel,
                onSearch = { /* optional */ },
                navController = navController
            )
        }
        composable("update") {
            UpdateScreen(
                viewModel = viewModel,
                onSearch = { /* optional */ },
                navController = navController
            )
        }
        composable("login") {
            LogInScreen(
                viewModel = viewModel,
                onSearch = { /* optional */ },
                navController = navController
            )
        }
    }
}

