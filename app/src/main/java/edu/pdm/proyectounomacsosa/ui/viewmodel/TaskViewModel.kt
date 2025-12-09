package edu.pdm.proyectounomacsosa.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.pdm.proyectounomacsosa.data.remote.RetrofitClient
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.data.repository.TaskRepository
import edu.pdm.proyectounomacsosa.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    var token ="" // "Bearer ... "
    var listaUsuario = mutableStateOf(listOf<User>())
    private set

    // Load tasks from API
    fun loadTasks() {
        viewModelScope.launch {
            println("Entra a load tasks")
            _uiState.update { it.copy(isLoading = true, message = "Cargando...") }
                try {
                    println("Entra al try")
                    println("Token: $token")
                    val userId = listaUsuario.value.firstOrNull()?.id
                    println("User id: $userId")
                    if (userId == null || token.isNullOrEmpty()) {
                        println("No hay usuario logueado o token inválido")
                        return@launch
                    }
                    val result = RetrofitClient.api.getTasks(
                        token= token,
                        user_id = userId ,
                    )
                    listaTasks.value = result
                } catch (e: Exception) {
                    e.printStackTrace()
                    println("Error: $e")
                }
            _uiState.update { it.copy(isLoading = false, message = "Carga completa") }
            println("Sale de load tasks")
        }
    }

    // Load task by ID from API
    fun findTaskById(ID: Int) {
        viewModelScope.launch {
            //taskUnica.value = repository.getById(ID)
            println("Entra a find by id")
            _uiState.update { it.copy(isLoading = true, message = "Cargando...") }
            try {
                println("Entra al try")
                taskUnica.value = RetrofitClient.api.getTaskById(token,ID)
            }
            catch (e: Exception) {
                e.printStackTrace()
                println("Error: $e")
            }
            _uiState.update { it.copy(isLoading = false, message = "Carga completa") }
            println("Sale de load tasks")
        }
    }

    // Load task by name from API
    fun findTaskByName(name: String) {
        viewModelScope.launch {
            taskUnica.value = repository.getByName(name)
        }
    }

    // Add task to API
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

    // Delete task from API
    fun eraseTask(ID: Int){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "Cargando...") }
            println("Entra a erase task")
            try {
                println("Entra al try")
                println("Token: $token")
                println("ID: $ID")
                RetrofitClient.api.deleteTask(token,ID)
            }
            catch (e: Exception) {
                e.printStackTrace()
                println("Error: $e")
            }
            _uiState.update { it.copy(isLoading = false, message = "Carga completa") }        }
    }

    // Update task from API
    fun updateTask(task: Task) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "Cargando...") }
            println("Entra a update task")
            println("nombre ${task.name}")
            println("fecha ${task.plannedD}")
            println("estado ${task.status}")
            println("id ${idUpVM}")
            try {
                RetrofitClient.api.updateTask(token,idUpVM, task)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
            _uiState.update { it.copy(isLoading = false, message = "Carga completa") }
        }
    }

    // Login user
    suspend fun login(loginUser: User): Boolean {
        _uiState.update { it.copy(isLoading = true, message = "Cargando...") }
        println("Entra a login vm")

        return try {
            val response = RetrofitClient.api.login(loginUser)
            token = "Bearer ${response.token}"
            val userData :User = response.user
            println("Token: $token")
            println("User: $userData")
            listaUsuario.value = listOf(userData) // solo un usuario activo
            println("lista usuario: $listaUsuario")

            _uiState.update { it.copy(isLoading = false, message = "Carga completa") }
            true
        } catch (e: Exception) {
            println("No se pudo :c")
            e.printStackTrace()
            _uiState.update { it.copy(isLoading = false, message = "Carga completa") }
            false
        }
    }

}