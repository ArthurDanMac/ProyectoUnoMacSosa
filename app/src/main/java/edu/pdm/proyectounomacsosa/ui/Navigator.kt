package edu.pdm.proyectounomacsosa.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.pdm.proyectounomacsosa.model.TaskRepository
import edu.pdm.proyectounomacsosa.viewmodel.TaskViewModel

@Composable
fun Navigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicios") { DeleteScreen(viewModel(), { Unit },navController  ) }

    }
}

