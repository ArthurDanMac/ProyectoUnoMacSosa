package edu.pdm.proyectounomacsosa.data.repository

import edu.pdm.proyectounomacsosa.data.local.TaskDao
import edu.pdm.proyectounomacsosa.data.remote.apiclient.RetrofitClient
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.util.SyncPrefs

class TaskRepository (private val dao: TaskDao,
                      private val api: RetrofitClient,
                      private val prefs: SyncPrefs
){
    suspend fun insert(task: Task) = dao.insert(task) //insertUser(user)

    suspend fun getAll() = dao.getAll() //insertUser(user)

    suspend fun getById(id: Int) = dao.getById(id) //insertUser(user)

    suspend fun getByName(name:String) = dao.getByName(name) //insertUser(user)

    suspend fun delete(id:Int) = dao.delete(id) //insertUser(user)

}