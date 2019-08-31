package `in`.co.logicsoft.workmangersample

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    companion object {
        const val NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE"
    }

    override fun doWork(): Result {
        val message = inputData.getString(NOTIFICATION_MESSAGE)
        triggerNotification(message)
        return Result.success()
    }

    private fun triggerNotification(message: String?) {
        val builder = NotificationCompat.Builder(applicationContext, "100")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(0, builder.build())
        }
    }
}