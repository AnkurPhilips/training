package com.philips.training

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast

class ServiceNow : Service() {

    private val binderPipe = IMyBinder()

    inner class IMyBinder : Binder() {
        fun getService(): ServiceNow {
            return this@ServiceNow
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binderPipe
    }

    override fun onStartCommand(intent: Intent?,
                                flags: Int,
                                startId: Int): Int {
        val data = intent?.getStringExtra("DATA") ?: ""
        Toast.makeText(this,data, Toast.LENGTH_SHORT).show()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this,"Destroyed", Toast.LENGTH_LONG).show()
    }
}
