package edu.pdm.proyectounomacsosa.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import edu.pdm.proyectounomacsosa.data.local.AppDatabase
import edu.pdm.proyectounomacsosa.data.network.NetworkMonitor
import edu.pdm.proyectounomacsosa.data.remote.RetrofitClient
import edu.pdm.proyectounomacsosa.data.repository.TaskRepository
import kotlinx.coroutines.coroutineScope
import java.util.concurrent.TimeUnit

class TaskSyncWorker(
    private val ctx: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(ctx, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val db = AppDatabase.getDatabase(ctx)
            val repo = TaskRepository(
                db.task_dao(),
                RetrofitClient,
                NetworkMonitor(applicationContext)
            )
            val tareas = repo.getLocalTasks()
            println("Tareas: $tareas")
            repo.sync()

            Result.success()
        } catch (e: Exception) {
            println("Error: $e")
            e.printStackTrace()
            Result.retry()
        }
    }
}


