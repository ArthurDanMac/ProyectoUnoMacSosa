package edu.pdm.proyectounomacsosa.data.repository

import edu.pdm.proyectounomacsosa.data.local.TaskDao
import edu.pdm.proyectounomacsosa.data.local.UserDAO
import edu.pdm.proyectounomacsosa.data.network.NetworkMonitor
import edu.pdm.proyectounomacsosa.data.remote.RetrofitClient
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.model.User
import kotlinx.coroutines.flow.first

class TaskRepository (private val taskDao: TaskDao,
                      private val userDAO: UserDAO,
                      private val rfClientApi: RetrofitClient,
                      private val networkMonitor: NetworkMonitor
){
    suspend fun sync() {
        // Verificar conexión antes de sincronizar
        val online = networkMonitor.isOnline.first()
        println("Online: $online")
        if (!online) return

        pullUsersFromServer()
        pullTasksFromServer()
        pushToServer()
    }

    suspend fun pullUsersFromServer() {
        val remoteUsers = rfClientApi.apiUser.getAllUsers(
            token =  "Bearer Admin123"
        ) //interface
        println("Remote tasks: $remoteUsers")

        val localUsers = userDAO.getAll()?.associateBy { it.id }
        println("Local tasks: $localUsers")

        val merged = remoteUsers.map { remote ->
            val local = localUsers?.get(remote.id)
            if (local == null ){    // || remote.lastUpdated > local.lastUpdated ) {
                println("Antes de clase Task: $remote")
                User(
                    id = remote.id,
                    username = remote.username,
                    hashedpsswd = remote.hashedpsswd,
                    email = remote.email
                )
            } else {
                local
            }
        }
        userDAO.insertAll(merged)
    }

    suspend fun pullTasksFromServer() {
        val remoteTasks = rfClientApi.apiTask.getAllTasks(
            token =  "Bearer Admin123"
        ) //interface
        println("Remote tasks: $remoteTasks")

        val localTasks = taskDao.getAll()?.associateBy { it.id }
        println("Local tasks: $localTasks")

        val merged = remoteTasks.map { remote ->
            val local = localTasks?.get(remote.id)
            if (local == null ){    // || remote.lastUpdated > local.lastUpdated ) {
                println("Antes de clase Task: $remote")
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
        println("merged: $merged")
        taskDao.insertAll(merged)
    }

    suspend fun pushToServer() {
        taskDao.getAll()?.forEach { task ->
            rfClientApi.apiTask.updateTask(
                token = "Bearer Admin123",
                idTask = task.id,
                task= task.toRemote()
            )
        }
    }

    private fun Task.toRemote() = Task(
        id, name, plannedD, status, user_id
    )
    suspend fun getLocalTasks(): List<Task>? = taskDao.getAll()

    suspend fun addLocalTask(task: Task) = taskDao.insert(task)

    suspend fun getById(id: Int) = taskDao.getById(id) //insertUser(user)

    suspend fun getByName(name:String) = taskDao.getByName(name) //insertUser(user)

    suspend fun delete(id:Int) = taskDao.delete(id) //insertUser(user)

}