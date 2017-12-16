package teachertracker.com.teachertracker_teacher

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import teachertracker.com.teachertracker_teacher.utils.GPSInfo
import java.util.*
import kotlin.concurrent.schedule
import kotlin.properties.Delegates

class WeatherService : Service() {
    var handler = Handler()
    var timer = Timer()
    var gpsInfo by Delegates.notNull<GPSInfo>()
    var runnable by Delegates.notNull<Runnable>()
    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        gpsInfo = GPSInfo(applicationContext)
        runnable = Runnable {
            Log.e("location lat", "${gpsInfo.getLatitude()}")
            Log.e("location lon", "${gpsInfo.getLongitude()}")
            weatherChangedListener?.getLocation()
        }
        timer.schedule(0, 500) {
            handler.post(runnable)
        }
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("asdf", "service")
        timer.purge()
        timer.schedule(0, 500) {
            handler.post(runnable)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    companion object {
        var weatherChangedListener: WeatherChangedListener? = null
    }
}
