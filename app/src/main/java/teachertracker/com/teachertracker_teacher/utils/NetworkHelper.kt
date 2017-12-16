package teachertracker.com.teachertracker_teacher.utils

import android.content.Context
import android.net.ConnectivityManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by eka on 2017. 12. 16..
 */
class NetworkHelper(var context: Context) {
    companion object {
        val url = ""
        val weatherUrl = "http://apis.skplanetx.com"
        var retrofit: Retrofit? = null
        var retrofitWeather: Retrofit? = null
        val networkInstance: NetworkAPI
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
                return retrofit!!.create(NetworkAPI::class.java)
            }
        val networkInstanceWeather: NetworkAPI
            get() {
                if (retrofitWeather == null) {
                    retrofitWeather = Retrofit.Builder()
                            .baseUrl(weatherUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
                return retrofitWeather!!.create(NetworkAPI::class.java)
            }

        fun returnNetworkState(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
        }
    }
}