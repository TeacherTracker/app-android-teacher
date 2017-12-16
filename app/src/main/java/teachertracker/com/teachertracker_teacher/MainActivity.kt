package teachertracker.com.teachertracker_teacher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import org.jetbrains.anko.image
import org.jetbrains.anko.startService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import teachertracker.com.teachertracker_teacher.databinding.ItemDailyBinding
import teachertracker.com.teachertracker_teacher.databinding.ItemHourlyBinding
import teachertracker.com.teachertracker_teacher.utils.GPSInfo
import teachertracker.com.teachertracker_teacher.utils.NetworkHelper
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var hourlyLastAdapter: LastAdapter
    private lateinit var dailyLastAdapter: LastAdapter
    private var hourlyItems = ArrayList<ItemHourly>()
    private var dailyItems = ArrayList<ItemDaily>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        setSupportActionBar(mainToolbar)

        supportActionBar?.let {
            title = ""
        }

        WeatherService.weatherChangedListener = object : WeatherChangedListener {
            override fun getWeather() {
            }

            override fun getLocation() {
                Log.e("hihi", "hihi")
            }
        }
        startService<WeatherService>()

        val gpsInfo = GPSInfo(this)

        Log.e("lat lon", "${gpsInfo.getLatitude()}  ${gpsInfo.getLongitude()}")

        Log.e("json", TestJson.json.getJSONObject("weather").getJSONArray("minutely").optJSONObject(0).getString("station"))

        hourlyRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        dailyRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)



        getHourlyWeather(gpsInfo.getLatitude(), gpsInfo.getLongitude())
        getDailyWeather(gpsInfo.getLatitude(), gpsInfo.getLongitude())

    }

    fun getDailyWeather(lat: Double, lon: Double) {
        NetworkHelper.networkInstanceWeather.getDailyWeather(1, lat, lon).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val str = response?.body()?.string()
                var json = JSONObject(str).getJSONObject("weather")
                        .getJSONArray("forecast6days")
                        .optJSONObject(0)

                dailyLastAdapter = LastAdapter(dailyItems, BR.item)
                        .map<ItemDaily, ItemDailyBinding>(R.layout.item_daily) {
                            onBind {
                                it.binding.img.image = (resources.getDrawable(R.drawable.bg_main))
                            }
                        }
                        .into(dailyRecyclerView)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
            }
        })
    }

    fun getHourlyWeather(lat: Double, lon: Double) {
        NetworkHelper.networkInstanceWeather.getHourlyWeather(1, lat, lon).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val str = response?.body()?.string()
                var json = JSONObject(str).getJSONObject("weather")
                        .getJSONArray("forecast3days")
                        .optJSONObject(0)
                Log.e("ASDf", json.toString())
                textViewCity.text = json.getJSONObject("grid").getString("city")
                textViewCounty.text = "${json.getJSONObject("grid").getString("county")} ${json.getJSONObject("grid").getString("village")}"

                var time = Date()
                (4..46)
                        .filter { it % 3 == 1 && time.hours <= it }
                        .forEach { textViewTemperature.text = json.getJSONObject("fcst3hour").getJSONObject("temperature").getString("temp${it}hour") }


                var count = 0
                for (i in 4..46) {
                    if (count >= 7)
                        break
                    if (i % 3 == 1 && time.hours <= i) {
                        var time = "${i}시"
                        val temp = json.getJSONObject("fcst3hour").getJSONObject("temperature").getString("temp${i}hour")
                        if (count == 0)
                            time = "지금"
                        hourlyItems.add(ItemHourly(time, R.drawable.bg_main, temp))
                        count++
                    }
                }
                textViewDay.text = SimpleDateFormat("E").format(time) + "요일"
                textViewDate.text = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(time)
                textViewMinTemperature.text = json.getJSONObject("fcstdaily").getJSONObject("temperature").getString("tmin1day")
                Log.e("asdfasdf", "1" + json.getJSONObject("fcstdaily").getJSONObject("temperature").getString("tmin1day"))
                textViewMaxTemperature.text = json.getJSONObject("fcstdaily").getJSONObject("temperature").getString("tmax1day")
                hourlyLastAdapter = LastAdapter(hourlyItems, BR.item)
                        .map<ItemHourly, ItemHourlyBinding>(R.layout.item_hourly) {
                            onBind {
                                it.binding.img.image = (resources.getDrawable(R.drawable.bg_main))
                            }
                        }
                        .into(hourlyRecyclerView)

            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
            }
        })
    }
}
