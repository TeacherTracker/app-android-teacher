package teachertracker.com.teachertracker_teacher.utils

/**
 * Created by eka on 2017. 12. 16..
 */
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.util.Log

@SuppressLint("Registered")
class GPSInfo(private val mContext: Context) : Service(), LocationListener {

    // flag for GPS status
    private var isGPSEnabled = false

    // flag for network status
    internal var isNetworkEnabled = false

    internal var canGetLocation = false

    lateinit internal var location: Location // location
    internal var latitude: Double = 0.toDouble() // latitude
    internal var longitude: Double = 0.toDouble() // longitude
    var locationManager: LocationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    init {
        requestLocation()
    }

    @SuppressLint("MissingPermission")
    fun requestLocation(): Location? {
        if (locationManager != null) {
            locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGPSEnabled || !isNetworkEnabled) {
                return null
            } else {
                this.canGetLocation = true
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            0.toLong(),
                            0.toFloat(), this)
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    latitude = location.latitude
                    longitude = location.longitude
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                0.toLong(),
                                0.toFloat(), this)
                        Log.d("GPS Enabled", "GPS Enabled")
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        latitude = location.latitude
                        longitude = location.longitude
                    }
                }
            }
            return location

        } else {
            Log.e("asdf", "No LocationManger Provided")
            return null
        }
    }

    fun getLatitude(): Double {

        requestLocation()
        latitude = location.latitude
        // return latitude
        return latitude
    }

    /**
     * Function to get longitude
     */
    fun getLongitude(): Double {
        requestLocation()
        longitude = location.longitude
        return longitude
    }

    fun canGetLocation(): Boolean {
        return this.canGetLocation
    }

    /**
     * Function to show settings alert dialog
     */
    fun showSettingsAlert(activity: Activity) {
        val alertDialog = AlertDialog.Builder(mContext)
        alertDialog.setTitle("GPS가 활성화되지 않았습니다.")
        alertDialog.setMessage("설정으로 이동하여 GPS를 허용하시겠습니까?")
        alertDialog.setPositiveButton("설정으로 이동") { _, _ ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            mContext.startActivity(intent)
            activity.finish()
        }

        alertDialog.setNegativeButton("취소") { dialog, _ ->
            run {
                dialog.cancel()
                activity.finish()
            }
        }

        alertDialog.show()
    }

    override fun onLocationChanged(location: Location) {
        Log.e("asdf", "Location Updated " + location.latitude + " " + location.longitude)
    }

    override fun onProviderDisabled(provider: String) {}

    override fun onProviderEnabled(provider: String) {}

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    companion object {

        // The minimum distance to change Updates in meters
        private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 0.1.toLong() // 10 meters

        // The minimum time between updates in milliseconds
        private val MIN_TIME_BW_UPDATES = 1.toLong() // 1 minute
    }
}