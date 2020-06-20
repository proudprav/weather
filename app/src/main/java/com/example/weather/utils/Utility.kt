package com.example.weather.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.io.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Utility {

    companion object {
        const val APP_ID = "5ad7218f2e11df834b0eaf3a33a39d2a"

        fun Double.convertToCelsius(feelsLike: Double) =
            "${String.format("%.1f",(this-273.0))}°C"
        fun Int.convertToDate(): String{
            val dateFormat: DateFormat = SimpleDateFormat("hh:mm a")
            val date = Date(this.toLong())
            return dateFormat.format(date).toString()
        }

         fun read(
            context: Context,
            fileName: String
        ): String? {
            return try {
                val fis: FileInputStream = context.openFileInput(fileName)
                val isr = InputStreamReader(fis)
                val bufferedReader = BufferedReader(isr)
                val sb = StringBuilder()
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                sb.toString()
            } catch (fileNotFound: FileNotFoundException) {
                null
            } catch (ioException: IOException) {
                null
            }
        }

         fun create(
            context: Context,
            fileName: String,
            jsonString: String?
        ): Boolean {
            return try {
                val fos: FileOutputStream =
                    context.openFileOutput(fileName, Context.MODE_PRIVATE)
                if (jsonString != null) {
                    fos.write(jsonString.toByteArray())
                }
                fos.close()
                true
            } catch (fileNotFound: FileNotFoundException) {
                false
            } catch (ioException: IOException) {
                false
            }
        }

        fun isFilePresent(
            context: Context,
            fileName: String
        ): Boolean {
            val path = context.filesDir.absolutePath + "/" + fileName
            val file = File(path)
            return file.exists()
        }

        fun checkForLocationPermission(activity: Activity){
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), 1
                )
            }
        }
    }
}