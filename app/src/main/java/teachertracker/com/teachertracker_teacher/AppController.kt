package teachertracker.com.teachertracker_teacher

import android.app.Application
import android.util.Log

/**
 * Created by eka on 2017. 12. 16..
 */
class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.e("Asdf", "application start")
    }
}