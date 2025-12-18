package edu.pdm.proyectounomacsosa.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.pdm.proyectounomacsosa.data.repository.TaskRepository
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocalViewModel(private val repository: TaskRepository) : ViewModel() {

    private val listaTasksLocal = MutableStateFlow<List<Task>>(emptyList() )
    val tasks: StateFlow<List<Task>> get() = listaTasksLocal

    val taskUnica = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> get() = taskUnica

    private var listaUsuariosLocales = MutableStateFlow<List<User>>(emptyList())
    var listaUserLocal = MutableStateFlow<List<User>>(emptyList())

    var l=0


    var idUpVM=0

    fun loadLocalTasks() {
        viewModelScope.launch {
            println("Entra a load local tasks")
            val idUser=listaUserLocal.value.first().id
            println("id: $idUser")
            listaTasksLocal.value = repository.getTaskByUserId( id = idUser )!!
            val prueba=repository.getLocalTasks()
            println("prueba: $prueba")

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
            listaUsuariosLocales.value = repository.getAllUsers()!!
            println("lista usuario local: $listaUsuariosLocales")

            // Itera sobre la lista y compara con el usuario que intenta hacer login
            for (user in listaUsuariosLocales.value) {
                println("lista usuario: ${user.username} ${user.password} ${user.email}")
                println("loginUser: ${loginUser.username} ${loginUser.password} ${loginUser.email}")
                if (user.email == loginUser.email && user.password == loginUser.password) {
                    listaUserLocal.value = listOf(user)
                    println("lista usuario local: $listaUserLocal")
                    println("lista usuario local id: ${listaUserLocal.value.first().id}")
                    println("lista usuario local username: ${listaUserLocal.value.first().username}")
                    return@async true // Devuelve true si las credenciales coinciden
                }
            }

            return@async false // Devuelve false si no se encontró el usuario
        }.await() // Espera el resultado de la corrutina y lo devuelve
    }


    fun updateTask(task: Task) {
        viewModelScope.launch {

            println("Entra a update task local")
            println("nombre ${task.name}")
            println("fecha ${task.plannedD}")
            println("estado ${task.status}")
            println("id ${idUpVM}")
            try {
                repository.update(task)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}