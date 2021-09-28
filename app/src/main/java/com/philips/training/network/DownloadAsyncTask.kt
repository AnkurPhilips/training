package com.philips.training.network

import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar
import androidx.core.widget.ContentLoadingProgressBar

class DownloadAsyncTask(private val progress: ProgressBar) : AsyncTask<Void, Int, Void>() {

    override fun onPreExecute() {
        super.onPreExecute()
        progress.visibility = View.VISIBLE
    }

    override fun doInBackground(vararg p0: Void?): Void? {

        for(i in 1..100){
            Thread.sleep(20)
            onProgressUpdate(i)
        }

        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        progress.visibility = View.GONE
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        val pr = values[0]
        if (pr != null) {
            progress.progress = pr
        }
    }


}
