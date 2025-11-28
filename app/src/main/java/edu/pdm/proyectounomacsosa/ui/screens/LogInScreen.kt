package edu.pdm.proyectounomacsosa.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.pdm.proyectounomacsosa.model.User
import edu.pdm.proyectounomacsosa.ui.components.TopRightMenu
import edu.pdm.proyectounomacsosa.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(viewModel: TaskViewModel, onSearch: () -> Unit, navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }




    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Log In") },
                actions = {
                    TopRightMenu(navController, "LogIn")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            // --- FORM ---
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(50.dp)) // pushes button down
            Button(
                onClick = {
                    if (username.isNotBlank() && password.isNotBlank() && email.isNotBlank()) {
                        val loginUser = User(
                            username = username,
                            password = password,
                            email = email
                        )
                        viewModel.login(loginUser)
                        if(viewModel.token.isNotBlank()) {
                            println("Token: ${viewModel.token}")
                            navController.navigate("seeTasks")
                        }else{
                            showErrorDialog = true
                        }
                    }
                },
                modifier =
                    Modifier.align(Alignment.CenterHorizontally)
                        .width(200.dp)                        // button width
                        .height(50.dp)
                        .fillMaxWidth(0.5f)  // 50% of the column width
                        .height(50.dp)
            ) {
                Text("Log In")
            }
            if (showErrorDialog) { //se pone un alert afuera del bloque del boton porque es composable y el if debe ir afuera, entre bloques de "display"
                AlertDialog(
                    onDismissRequest = { showErrorDialog = false },
                    title = { Text("Error de LogIn") },
                    text = { Text("Inconsistencia de datos") },
                    confirmButton = {
                        Button(onClick = { showErrorDialog = false }) {
                            Text("Aceptar")
                        }
                    }
                )
            }
        }
    }
}
