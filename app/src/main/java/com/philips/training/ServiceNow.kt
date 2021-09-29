package com.philips.training

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi

class ServiceNow : Service() {

    private val binderPipe = IMyBinder()

    private var service: MediaPlayer? = null

    inner class IMyBinder : Binder() {
        fun getService(): ServiceNow {
            return this@ServiceNow
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBind(intent: Intent): IBinder {
        startPlaying()
        return binderPipe
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun startPlaying() {
        if(service == null) {
            service = MediaPlayer.create(this, R.raw.audio)
        }
        service?.start()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStartCommand(intent: Intent?,
                                flags: Int,
                                startId: Int): Int {
        val data = intent?.getStringExtra("DATA") ?: ""
        Toast.makeText(this,data, Toast.LENGTH_SHORT).show()
        startPlaying()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        service?.stop()
        service = null
        Toast.makeText(this,"Destroyed", Toast.LENGTH_LONG).show()
    }
}
