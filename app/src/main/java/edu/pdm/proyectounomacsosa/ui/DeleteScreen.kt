package edu.pdm.proyectounomacsosa.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.viewmodel.TaskViewModel
import kotlin.properties.Delegates

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteScreen(viewModel: TaskViewModel, onSearch: () -> Unit, navController: NavHostController) {
    val tareas by viewModel.tasks.collectAsState()
    var reloadKey by remember { mutableStateOf(0) }
    var id_=0
    var state by remember { mutableStateOf(false) }

    LaunchedEffect(reloadKey) { viewModel.loadTasks() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Listas de Tarea por Hacer") }
            )
        }
    ) { padding ->
        LazyColumn(Modifier.selectableGroup()) {
            items(tareas) { tar ->
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp,4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = tar.name,
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    RadioButton(
                        selected = state,
                        onClick = {
                            state = true
                            tareas.map {
                                id_=it.id
                            }

                        }
                    )
                    state=false
                }
            }
        }
        Button(
            onClick = { reloadKey++ }
        ) {
            var VM=viewModel.eraseTask(id_)
            viewModel.loadTasks()
            Text("Borrar tarea")
        }
    }

}