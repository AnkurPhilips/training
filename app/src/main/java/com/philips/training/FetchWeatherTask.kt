package com.philips.training

import android.os.AsyncTask
import android.widget.EditText
import android.widget.Toast
import com.philips.training.network.NetworkUtils

class FetchWeatherTask(private val weather: EditText): AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg p0: String?): String {
        return p0[0]?.let { NetworkUtils.getWeatherData(it) } ?: ""
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Toast.makeText(weather.context, "Result : $result", Toast.LENGTH_LONG).show()
    }

}
