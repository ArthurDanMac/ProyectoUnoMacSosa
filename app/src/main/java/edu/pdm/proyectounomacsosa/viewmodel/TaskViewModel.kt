package edu.pdm.proyectounomacsosa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.pdm.proyectounomacsosa.apiclient.RetrofitClient
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.model.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.plus

class TaskViewModel (private val repository: TaskRepository) : ViewModel(){
    data class UiState(
        val isLoading: Boolean = false,
        val message: String = "Presiona el botón"
    )

    private val listaTasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = listaTasks

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    val taskUnica = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> get() = taskUnica
    private val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InJvb3QiLCJpYXQiOjE3NjI1NjY0NjYsImV4cCI6MTc2MjU3MDA2Nn0.sQF1_qttqowhkw3dzsCTcNsurOQ-5WRULbcO34zGuzg"
    fun loadTasks() {
        viewModelScope.launch {
            //listaTasks.value = repository.getAll()!!
            println("Entra a load tasks")
            _uiState.update { it.copy(isLoading = true, message = "Cargando...") }
           // var result = withContext(Dispatchers.IO) {

                try {
                    println("Entra al try")
                    val result2 = RetrofitClient.api.getTasks(token)
                    listaTasks.value = result2
                } catch (e: Exception) {
                    e.printStackTrace()
                    println("Error: $e")
                }

            //}

            _uiState.update { it.copy(isLoading = false, message = "Carga completa") }

            println("Sale de load tasks")

        }
    }


    fun findTaskById(ID: Int) {
        viewModelScope.launch {
            taskUnica.value = repository.getById(ID)
        }
    }

    fun findTaskByName(name: String) {
        viewModelScope.launch {
            taskUnica.value = repository.getByName(name)
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
//            repository.insert(task)
//            loadTasks()
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
            var num=repository.delete(ID)
        }

    }

}