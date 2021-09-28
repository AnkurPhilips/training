package com.philips.training.network

import android.net.Uri
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

/**
 * (C) Koninklijke Philips N.V., 2021.
 * All rights reserved.
 */
object NetworkUtils {

    fun getWeatherData(cityName: String): String {

        val apiKey = "b8f758488a91220157468e3647c3e648"
        val root = "api.openweathermap.org/data/2.5/weather?"
        val queryString = "q"
        val appId = "appid"

        val uri = Uri.parse(root).buildUpon()
            .appendQueryParameter(queryString,cityName)
            .appendQueryParameter(appId, apiKey).build()

        return fetchResultFromURI(uri)
    }

    fun getBookInfo(bookName: String): String {

        val root = "https://www.googleapis.com/books/v1/volumes?"
        val query = "q"
        val maxResults = "maxResults"
        val printType = "printType"

        val uriBuilder = Uri.parse(root).buildUpon()
            .appendQueryParameter(query,bookName)
            .appendQueryParameter(maxResults,"3")
            .appendQueryParameter(printType,"books")

        val uri = uriBuilder.build()

        return fetchResultFromURI(uri)
    }

    private fun fetchResultFromURI(uri: Uri): String {

        var result = ""

        val urlConnection: HttpURLConnection
        val bufferReader: BufferedReader

        try {
            val url = URL(uri.toString())

            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"

            urlConnection.connect()

            bufferReader = BufferedReader(InputStreamReader(urlConnection.inputStream))

            val buffer = StringBuffer()

            var line = bufferReader.readLine()

            while (line != null) {
                buffer.append(line)
                line = bufferReader.readLine()
            }
            urlConnection.disconnect()
            bufferReader.close()

            result = buffer.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {

            return result
        }
    }

}