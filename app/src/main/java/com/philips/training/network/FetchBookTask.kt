package com.philips.training.network

import android.os.AsyncTask
import android.widget.EditText
import android.widget.Toast
import org.json.JSONObject
import java.lang.Exception

class FetchBookTask(private val search: EditText) : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg p0: String?): String {

        return p0[0]?.let { NetworkUtils.getBookInfo(it) } ?: ""
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        try {
            val itemArray = JSONObject(result).getJSONArray("items")

            var authors = ""

            for(i in 0 until itemArray.length()) {
                val jsonElement = itemArray.get(i) as? JSONObject
                val volumeInfo = jsonElement?.getJSONObject("volumeInfo")
                val title = volumeInfo?.getString("title").toString()
                val author = volumeInfo?.getJSONArray("authors").toString()

                authors = "$authors; $title : $author"
            }
            Toast.makeText(search.context, "Result : $authors", Toast.LENGTH_LONG).show()


        } catch (e: Exception){
            e.printStackTrace()
        }
    }

}
