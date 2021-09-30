package com.philips.training

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.philips.training.db.EntriesDao
import com.philips.training.db.NoteEntry
import com.philips.training.network.DownloadAsyncTask
import com.philips.training.network.FetchBookTask
import kotlinx.android.synthetic.main.activity_main.*

const val NAME="name"
const val PASSWORD="password"


private const val REQUEST_SMS_READ: Int = 1009

class MainActivity : AppCompatActivity() {
    private lateinit var entriesDao: EntriesDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        entriesDao = EntriesDao(this)
        entriesDao.openDB()
    }

    override fun onStart() {
        super.onStart()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "Firebase $token"
            Log.d(TAG, msg)
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        })

        button.setOnClickListener {

            val name = tv.text.toString()
            val password = et.text.toString()
            if(name.isNotBlank() && password.isNotBlank()) {
                entriesDao.insertEntry(NoteEntry(name))
                val intent = Intent(this, OtherActivity::class.java)
                intent.putExtra(NAME, name)
                intent.putExtra(PASSWORD, password)
                startActivity(intent)
            }
        }

        download.setOnClickListener {
            val downLoadAsyncTask = DownloadAsyncTask(progress)
            downLoadAsyncTask.execute()
        }

        callApi.setOnClickListener {
            val bookName = search.text.toString()
            val fetchBookTask = FetchBookTask(search)
            fetchBookTask.execute(bookName)
        }

        weatherApi.setOnClickListener{
            val city = weather.text.toString()
            val fetchWeatherTask = FetchWeatherTask(weather)
            fetchWeatherTask.execute(city)
        }

        start.setOnClickListener {
            val intent = getServiceIntent(this)
            val data = tv.text.toString()
            intent.putExtra("DATA", data)
            startService(intent)
        }

        stop.setOnClickListener {
            val intent = getServiceIntent(this)
            stopService(intent)
        }

        try {
            val permission = listOf(Manifest.permission.READ_CALL_LOG)
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, permission.toTypedArray(), REQUEST_SMS_READ)
            }else{
                readMessages()
            }

        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        readMessages()
    }

    private fun readMessages() {
        val uri = CallLog.Calls.CONTENT_URI
        val cursor = contentResolver.query(uri, null, null, null, null, null)
        cursor?.let {
            val bodyIndex = cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME)
            val addressIndex = cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER)
            while (cursor.moveToNext()) {
                val data = cursor.getString(bodyIndex) + " : " + cursor.getString(addressIndex)
                Log.i("SMS Messages", data)
            }
        }
        cursor?.close()
    }

    companion object {
        fun getServiceIntent(context: Context) = Intent(context, ServiceNow::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        entriesDao.closeDB()
    }
}