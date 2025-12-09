package edu.pdm.proyectounomacsosa.data.repository

import edu.pdm.proyectounomacsosa.data.local.TaskDao
import edu.pdm.proyectounomacsosa.data.network.NetworkMonitor
import edu.pdm.proyectounomacsosa.data.remote.apiclient.TaskApiService
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.first

class TaskRepository (private val dao: TaskDao,
                      private val api: TaskApiService,
                      private val networkMonitor: NetworkMonitor
){
    suspend fun sync() {
        // Verificar conexión antes de sincronizar
        val online = networkMonitor.isOnline.first()
        if (!online) return

        pullFromServer()
        pushToServer()
    }

    suspend fun pullFromServer() {
        val remoteTasks = api.getTasks(
            token =  "Bearer 123",
            user_id = 1
        ) //interface
        val localTasks = dao.getAll()?.associateBy { it.id }

        val merged = remoteTasks.map { remote ->
            val local = localTasks?.get(remote.id)
            if (local == null ){    // || remote.lastUpdated > local.lastUpdated ) {
                Task(
                    id = remote.id,
                    name = remote.name,
                    status = remote.status,
                    plannedD = remote.plannedD,
                    user_id = remote.user_id
                )
            } else {
                local
            }
        }
        dao.insertAll(merged)
    }

    suspend fun pushToServer() {
        dao.getAll()?.forEach { task ->
            api.updateTask(
                token = "Bearer ",
                idTask = task.id,
                task= task.toRemote()
            )
        }
    }

    private fun Task.toRemote() = Task(
        id, name, plannedD, status, user_id
    )
    suspend fun getLocalTasks(): List<Task>? = dao.getAll()

    suspend fun addLocalTask(task: Task) = dao.insert(task)

    suspend fun getById(id: Int) = dao.getById(id) //insertUser(user)

    suspend fun getByName(name:String) = dao.getByName(name) //insertUser(user)

    suspend fun delete(id:Int) = dao.delete(id) //insertUser(user)

}