package edu.pdm.proyectounomacsosa.model

class TaskRepository (private val dao: TaskDao){
    suspend fun insert(task: Task) = dao.insert(task) //insertUser(user)

    suspend fun getAll() = dao.getAll() //insertUser(user)

    suspend fun getById(id: Int) = dao.getById(id) //insertUser(user)

    suspend fun getByName(name:String) = dao.getByName(name) //insertUser(user)

    suspend fun delete(id:Int) = dao.delete(id) //insertUser(user)

}