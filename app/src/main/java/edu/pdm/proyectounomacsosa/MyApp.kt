package edu.pdm.proyectounomacsosa

import android.app.Application
import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import edu.pdm.proyectounomacsosa.data.worker.TaskSyncWorker
import java.util.concurrent.TimeUnit


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        scheduleSync(this)
    }
    fun scheduleSync(context: Context) {
        val work = PeriodicWorkRequestBuilder<TaskSyncWorker>(15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "task_sync",
            ExistingPeriodicWorkPolicy.UPDATE,
            work
        )
    }
}