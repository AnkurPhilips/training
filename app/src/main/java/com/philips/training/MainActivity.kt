package com.philips.training

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.philips.training.network.DownloadAsyncTask
import com.philips.training.network.FetchBookTask
import kotlinx.android.synthetic.main.activity_main.*

const val NAME="name"
const val PASSWORD="password"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        button.setOnClickListener {

            val name = tv.text.toString()
            val password = et.text.toString()
            if(name.isNotBlank() && password.isNotBlank()) {
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
    }

    companion object {
        fun getServiceIntent(context: Context) = Intent(context, ServiceNow::class.java)
    }
}