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
import edu.pdm.proyectounomacsosa.ui.viewmodel.LocalViewModel
import edu.pdm.proyectounomacsosa.ui.viewmodel.RemoteViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigator(viewModelR: RemoteViewModel, viewModelL: LocalViewModel,navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("addTask") {
            AddTaskScreen(
                viewModel = viewModelR,
                onSearch = { /* optional */ },
                navController = navController
            )
        }
        composable("seeTasks") {
            TaskListScreen(
                remoteViewModel = viewModelR,
                localViewModel = viewModelL,
                onSearch = { /* optional */ },
                navController = navController
            )
        }
        composable("listById") {
            ListByIdScreen(
                viewModel = viewModelR,
                onSearch = { /* optional */ },
                navController = navController
            )
        }
        composable("delete") {
            DeleteScreen(
                viewModel = viewModelR,
                onSearch = { /* optional */ },
                navController = navController
            )
        }
        composable("update") {
            UpdateScreen(
                viewModel = viewModelR,
                onSearch = { /* optional */ },
                navController = navController
            )
        }
        composable("login") {
            LogInScreen(
                viewModelRemoto = viewModelR,
                viewModelLocal = viewModelL,
                onSearch = { /* optional */ },
                navController = navController
            )
        }
    }
}

