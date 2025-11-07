package edu.pdm.proyectounomacsosa.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun TopRightMenu(navController: NavHostController, screenName: String) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = screenName,
            fontSize = 24.sp,
            fontFamily = FontFamily.Monospace,       // choose any font family
            fontWeight = FontWeight.Bold,        // bold
            fontStyle = FontStyle.Normal,        // normal, italic, etc
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)           // optional spacing from top
        )
        // Este Box envuelve el botón y lo alinea arriba a la derecha
        Box(modifier = Modifier.align(Alignment.TopEnd)) {
            // Title text next to the menu

            Row(verticalAlignment = Alignment.CenterVertically) {


                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menú")
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
                //offset = DpOffset(x = (-40).dp, y = 0.dp) // Ajusta si el menú no se alinea bien
            ) {
                DropdownMenuItem(
                    text = { Text("Add Task") },
                    onClick = {
                        navController.navigate("addTask")
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Task List") },
                    onClick = {
                        navController.navigate("seeTasks")
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Task By ID") },
                    onClick = {
                        navController.navigate("listById")
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Delete Task") },
                    onClick = {
                        navController.navigate("delete")
                        expanded = false
                    }
                )
            }
        }
    }
}