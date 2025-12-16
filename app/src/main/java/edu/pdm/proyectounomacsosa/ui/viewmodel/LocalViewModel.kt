package edu.pdm.proyectounomacsosa.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.pdm.proyectounomacsosa.data.remote.RetrofitClient
import edu.pdm.proyectounomacsosa.data.repository.TaskRepository
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocalViewModel(private val repository: TaskRepository) : ViewModel() {

    private val listaTasksLocal = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = listaTasksLocal

    val taskUnica = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> get() = taskUnica

    var listaUsuarioLocal = mutableStateOf(listOf<User>())
        private set

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

    fun loginLocal(loginUser: User): Boolean {
        println("Entra a login vm")

        return try {
            val userData :User =loginUser
            println("User: $userData")
            listaUsuarioLocal.value = listOf(userData) // solo un usuario activo
            println("lista usuario: $listaUsuarioLocal")
            true
        } catch (e: Exception) {
            println("No se pudo :c")
            e.printStackTrace()
            false
        }
    }

}