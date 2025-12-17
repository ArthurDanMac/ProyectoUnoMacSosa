package edu.pdm.proyectounomacsosa.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Operation
import androidx.work.await
import edu.pdm.proyectounomacsosa.data.remote.RetrofitClient
import edu.pdm.proyectounomacsosa.data.repository.TaskRepository
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocalViewModel(private val repository: TaskRepository) : ViewModel() {

    private val listaTasksLocal = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = listaTasksLocal

    val taskUnica = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> get() = taskUnica

    private val listaUsuarioLocal = MutableStateFlow<List<User>>(emptyList())

    fun loadLocalTasks() {
        viewModelScope.launch {
            listaTasksLocal.value = repository.getLocalTasks()!!
        }
    }


    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.addLocalTask(task)
            loadLocalTasks()
        }
    }

    fun eraseLocalTask(ID: Int) {
        viewModelScope.launch {
            var num = repository.delete(ID)
        }

    }

    suspend fun loginLocal(loginUser: User): Boolean {
        // Usa async para obtener un resultado de la corrutina
        return viewModelScope.async(Dispatchers.IO) {
            println("Entra a login local")
            val listaUsuarioLocal = repository.getAllUsers() ?: emptyList()
            println("lista usuario local: $listaUsuarioLocal")

            // Itera sobre la lista y compara con el usuario que intenta hacer login
            for (user in listaUsuarioLocal) {
                println("lista usuario: ${user.username} ${user.password} ${user.email}")
                println("loginUser: ${loginUser.username} ${loginUser.password} ${loginUser.email}")
                if (user.email == loginUser.email && user.password == loginUser.password) {
                    return@async true // Devuelve true si las credenciales coinciden
                }
            }

            return@async false // Devuelve false si no se encontró el usuario
        }.await() // Espera el resultado de la corrutina y lo devuelve
    }



}