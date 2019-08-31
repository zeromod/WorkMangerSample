package `in`.co.logicsoft.workmangersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDateTime
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidThreeTen.init(this)

        setupWork()
    }

    private fun setupWork() {
        val constrains = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val now = LocalDateTime.now()
        val generatedAt = "Requested work at ${now.hour}:${now.minute}"
        val data = Data.Builder().putString(UploadWorker.NOTIFICATION_MESSAGE, generatedAt).build()

        val uploadWorkRequest = PeriodicWorkRequestBuilder<UploadWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constrains)
            .setInputData(data)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "my_unique_work",
            ExistingPeriodicWorkPolicy.KEEP,
            uploadWorkRequest
        )
    }
}
