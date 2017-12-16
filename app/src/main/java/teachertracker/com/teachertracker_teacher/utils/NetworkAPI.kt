package teachertracker.com.teachertracker_teacher.utils

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by eka on 2017. 12. 16..
 */
interface NetworkAPI {

    @Headers("appKey:157ed9cc-7ad0-3c87-a94a-05ed0cee8d03")
    @GET("/weather/forecast/3days")
    fun getHourlyWeather(@Query("version") version: Int, @Query("lat") lat: Double, @Query("lon") lon: Double): Call<ResponseBody>


    @Headers("appKey:157ed9cc-7ad0-3c87-a94a-05ed0cee8d03")
    @GET("/weather/forecast/6days")
    fun getDailyWeather(@Query("version") version: Int, @Query("lat") lat: Double, @Query("lon") lon: Double): Call<ResponseBody>

}