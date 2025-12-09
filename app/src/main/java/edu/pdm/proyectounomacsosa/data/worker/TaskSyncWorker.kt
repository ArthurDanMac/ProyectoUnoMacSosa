package edu.pdm.proyectounomacsosa.data.worker

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class TaskSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        //val repo = AppContainer.get().taskRepository
        return try {
           // repo.sync()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}

