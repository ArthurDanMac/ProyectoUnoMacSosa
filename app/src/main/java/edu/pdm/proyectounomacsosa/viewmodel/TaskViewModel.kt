package edu.pdm.proyectounomacsosa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.model.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel (private val repository: TaskRepository) : ViewModel(){

    private val listaTasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = listaTasks

    private val taskUnica = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> get() = taskUnica

    fun loadTasks() {
        viewModelScope.launch {
            listaTasks.value = repository.getAll()!!
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
            repository.insert(task)
            loadTasks()
        }
    }



}