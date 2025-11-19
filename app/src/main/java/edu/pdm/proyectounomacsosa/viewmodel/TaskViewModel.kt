package edu.pdm.proyectounomacsosa.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.pdm.proyectounomacsosa.apiclient.RetrofitClient
import edu.pdm.proyectounomacsosa.apiclient.TaskApiService
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.model.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetAddress
import kotlin.collections.plus

class TaskViewModel (private val repository: TaskRepository) : ViewModel(){
    data class UiState(
        val isLoading: Boolean = false,
        val message: String = "Presiona el botón"
    )

    var idUpVM=0
    private val listaTasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = listaTasks

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    val taskUnica = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> get() = taskUnica
    private val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InJvb3QiLCJpYXQiOjE3NjMwNTE1MTYsImV4cCI6MTc5NDYwOTExNn0.y5q1uYHElp6-kMHNnMSRXaew1qPuvyvKmAJvZrYV3k0"


    fun loadTasks() {
        viewModelScope.launch {
            println("Entra a load tasks")
            _uiState.update { it.copy(isLoading = true, message = "Cargando...") }
                try {
                    println("Entra al try")
                    val result2 = RetrofitClient.api.getTasks(token)
                    listaTasks.value = result2
                } catch (e: Exception) {
                    e.printStackTrace()
                    println("Error: $e")
                }
            _uiState.update { it.copy(isLoading = false, message = "Carga completa") }
            println("Sale de load tasks")
        }
    }


    fun findTaskById(ID: Int) {
        viewModelScope.launch {
            //taskUnica.value = repository.getById(ID)
            println("Entra a load tasks")
            _uiState.update { it.copy(isLoading = true, message = "Cargando...") }
            try {
                println("Entra al try")
                taskUnica.value = RetrofitClient.api.getTaskById(token,ID)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error: $e")
            }
            _uiState.update { it.copy(isLoading = false, message = "Carga completa") }
            println("Sale de load tasks")
        }
    }

    fun findTaskByName(name: String) {
        viewModelScope.launch {
            taskUnica.value = repository.getByName(name)
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "Cargando...") }
            println("Entra a add task")
            println("nombre ${task.name}")
            println("fecha ${task.plannedD}")
            println("estado ${task.status}")
            try {
                val newTask = RetrofitClient.api.createTask(token, task)
                listaTasks.value = listaTasks.value + newTask
            } catch (e: Exception) {
                e.printStackTrace()
            }
            _uiState.update { it.copy(isLoading = false, message = "Carga completa") }
        }
    }

    fun eraseTask(ID: Int){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "Cargando...") }
            try {
                val oldTask = RetrofitClient.api.deleteTask(token,ID)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            _uiState.update { it.copy(isLoading = false, message = "Carga completa") }        }
    }


    var resolvedIp by mutableStateOf<String?>(null)
        private set

    fun resolveDomain() {
        val domain="https://proyecto-uno-mac-sosa.vercel.app/api/tasks"
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val ip = InetAddress.getByName(domain).hostAddress
                resolvedIp = ip
                println( "Resolved IP: $ip")
            } catch (e: Exception) {
                resolvedIp = "Error: ${e.message}"
                println( "Resolution failed: ${e.message}")
            }
        }
    }
    fun updateTask(task: Task) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "Cargando...") }
            println("Entra a add task")
            println("nombre ${task.name}")
            println("fecha ${task.plannedD}")
            println("estado ${task.status}")
            try {
                val newTask = RetrofitClient.api.updateTask(token,idUpVM, task)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            _uiState.update { it.copy(isLoading = false, message = "Carga completa") }
        }
    }



}