package edu.pdm.proyectounomacsosa.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import edu.pdm.proyectounomacsosa.ui.components.TopRightMenu
import edu.pdm.proyectounomacsosa.ui.viewmodel.TaskViewModel
import androidx.compose.material3.Checkbox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteScreen(viewModel: TaskViewModel, onSearch: () -> Unit, navController: NavHostController) {
    val tareas by viewModel.tasks.collectAsState()
    var reloadKey by remember { mutableStateOf(0) }

    // Track multiple selections
    var selectedIds by remember { mutableStateOf(setOf<Int>()) }

    LaunchedEffect(reloadKey) { viewModel.loadTasks() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Delete Tasks") },
                actions = { TopRightMenu(navController, "Delete Tasks") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyColumn {
                items(tareas) { tar ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tar.name,
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.bodyLarge,
                        )

                        // Use Checkbox instead of RadioButton
                        val isSelected = tar.id in selectedIds
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = { checked ->
                                selectedIds = if (checked) {
                                    selectedIds + tar.id
                                } else {
                                    selectedIds - tar.id
                                }
                            }
                        )
                    }
                }
            }

            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                enabled = selectedIds.isNotEmpty(),
                onClick = {
                    selectedIds.forEach { id ->
                        viewModel.eraseTask(id)
                    }
                    selectedIds = emptySet()
                    reloadKey++ // force reload
                    navController.navigate("seeTasks")
                }
            ) {
                Text("Borrar tareas seleccionadas")
            }
        }
    }
}
