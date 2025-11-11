package edu.pdm.proyectounomacsosa.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.pdm.proyectounomacsosa.ui.screens.AddTaskScreen
import edu.pdm.proyectounomacsosa.ui.screens.DeleteScreen
import edu.pdm.proyectounomacsosa.ui.screens.ListByIdScreen
import edu.pdm.proyectounomacsosa.ui.screens.RegisterScreen
import edu.pdm.proyectounomacsosa.viewmodel.TaskViewModel

@Composable
fun Navigator(viewModel: TaskViewModel, navController: NavHostController) {
    NavHost(navController, startDestination = "seeTasks") {
        composable("addTask") {
            AddTaskScreen(
                viewModel = viewModel,
                onSearch = { /* optional */ },
                navController = navController
            )
        }
        composable("seeTasks") {
            RegisterScreen(
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
    }
}