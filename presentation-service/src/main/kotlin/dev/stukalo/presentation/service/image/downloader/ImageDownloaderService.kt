package dev.stukalo.presentation.service.image.downloader

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import dev.stukalo.presentation.core.localization.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.io.File

const val NOTIFICATION_CHANNEL_NAME = "Image Download Notifications"
const val CHANNEL_ID = "image_download_channel"
const val NOTIFICATION_ID = 1


class ImageDownloaderService: Service() {

    private val imageDTO by inject<ImageDTO>()

    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(
            NOTIFICATION_ID,
            createNotification(
                getString(
                    R.string.image_downloader_downloading
                )
            )
        )

        downloadImage(imageDTO)

        return START_NOT_STICKY
    }

    private fun downloadImage(imageDTO: ImageDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            val directory = application.getDir(
                getString(R.string.downloads_dir_name),
                Context.MODE_PRIVATE
            )

            with(imageDTO) {

                val imageFileName = name ?: (System.currentTimeMillis()
                    .toString() + "_${compressionMethod}")

                val imageConfigFileName =
                    if (width != 0 && height != 0) imageFileName + getString(R.string.image_config_prefix)
                    else null

                val imageFile = File(directory, imageFileName)
                val imageConfigFile = imageConfigFileName?.let { File(directory, imageConfigFileName) }

                try {
                    imageFile.outputStream().apply { write(byteArray) }

                    imageConfigFile?.apply {
                        bufferedWriter().use { writer ->
                            writer.write(width.toString())
                            writer.newLine()
                            writer.write(height.toString())
                        }
                    }

                    updateNotification(getString(R.string.image_downloader_complete))
                } catch (e: Exception) {
                    e.printStackTrace()
                    updateNotification(getString(R.string.image_downloader_fail))
                } finally {
                    stopSelf()
                }
            }
        }
    }

    private fun createNotification(content: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.image_downloader_title))
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_menu_save)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun updateNotification(content: String) {
        val notification = createNotification(content)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    override fun onBind(intent: Intent?): IBinder? = null

}