package com.newsportal.info_6134capstoneproject.data

import android.os.AsyncTask
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

interface WeatherFetchListener {
    fun onWeatherFetched(name: String, tempC: String, weatherCondition: String, conditionCode: Int)
}

class FetchWeather(private val listener: WeatherFetchListener) {

    fun fetchWeatherByCity(cityName: String) {
        WeatherFetchTask(listener).execute(cityName)
    }

    fun fetchWeatherByCoordinates(latitude: Double, longitude: Double) {
        WeatherFetchTask(listener).execute("$latitude,$longitude")
    }

    private inner class WeatherFetchTask(private val listener: WeatherFetchListener) :
        AsyncTask<String, Void, JSONObject>() {

        override fun doInBackground(vararg params: String?): JSONObject? {
            val apiKey = "28aa7fe5e34d41abad463116242603"
            val location = params[0]
            val url = URL("https://api.weatherapi.com/v1/current.json?key=$apiKey&q=$location&aqi=no")

            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                inputStream.close()
                connection.disconnect()
                return JSONObject(stringBuilder.toString())
            }
            return null
        }

        override fun onPostExecute(result: JSONObject?) {
            super.onPostExecute(result)
            if (result != null) {
                val location = result.getJSONObject("location")
                val name = location.getString("name")
                val tempC = result.getJSONObject("current").getString("temp_c")
                val weatherCondition = result.getJSONObject("current").getJSONObject("condition").getString("text")
                val conditionCode = result.getJSONObject("current").getJSONObject("condition").getInt("code")

                listener.onWeatherFetched(name, tempC, weatherCondition, conditionCode)
            }
        }
    }
}


