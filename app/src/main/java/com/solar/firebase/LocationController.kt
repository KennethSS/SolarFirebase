package com.solar.firebase

import android.Manifest
import android.app.Activity
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import java.util.*

class LocationController(
        private val activity: Activity,
        private val locationManager: LocationManager,
        private val fusedLocationClient: FusedLocationProviderClient,
        private val observe: MutableLiveData<Location>) {

    private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Float = 10.0f
    private val MIN_TIME_BW_UPDATES = (1000 * 60 * 1).toLong()

    private fun isGrantedPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val isAccessFine = ActivityCompat.checkSelfPermission(activity,Manifest.permission.READ_PHONE_STATE)
            val isAccessCoarse = ActivityCompat.checkSelfPermission(activity,Manifest.permission.READ_PHONE_STATE)
            if (isAccessCoarse == PermissionChecker.PERMISSION_GRANTED || isAccessFine == PermissionChecker.PERMISSION_GRANTED) {
                return true
            } else {
                activity.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 0)
            }
        }

        return true
    }

    fun getLocation() {
        if (isGrantedPermission()) {
            /*fusedLocationClient.lastLocation
                    .addOnFailureListener {
                        Log.d("getLocation", "failure " + it.message)
                    }
                    .addOnSuccessListener { location : Location? ->
                        // 기기 설정에서 위치가 사용 중지되어 있습니다.
                        // 위치를 사용 중지하면 캐시도 지워지므로 이전에 마지막 위치를 가져온 경우에도 결과가 null이 될 수 있습니다.
                        // 기기에서 위치를 기록한 적이 없습니다. 예: 새 기기 또는 초기화 상태로 복원된 기기.
                        // 기기의 Google Play 서비스가 다시 시작되었으며 서비스가 다시 시작된 후 위치를 요청한 활성 통합 위치 정보 제공자 클라이언트가 없습니다.
                        // 이러한 상황이 발생하지 않도록 새 클라이언트를 만들고 직접 위치 업데이트를 요청할 수 있습니다. 자세한 내용은 위치 업데이트 받기를 참조하세요.

                        if (location == null) {
                            Log.d("getLocation", "location is null")
                            startLocationUpdates()
                        } else {
                            Log.d("getLocation", "location is not null")
                            observe.value = location
                        }
                    }*/
        }
    }

    private val locationListener: LocationListener = object: LocationListener {
        /*override fun onLocationChanged(location: Location?) {
            location?.let { observe.value = it }
        }*/

        override fun onLocationChanged(location: Location) {

        }

        /*override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}
        override fun onProviderEnabled(p0: String?) {}
        override fun onProviderDisabled(p0: String?) {}*/
    }

    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        numUpdates = 1
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = (60 * 1000).toLong()
        fastestInterval = (2 * 2000).toLong()
    }

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return

            if (locationResult.locations.size > 0) {
                Log.d("onLocationResult", "locationResult.locations.size > 0")
                observe.value = locationResult.locations[0]
            } else {
                Log.d("onLocationResult", "locationResult.locations.size < 0")
                //getLocationFromLocationManager()
            }
        }

        override fun onLocationAvailability(p0: LocationAvailability?) {
            super.onLocationAvailability(p0)
        }
    }

    @RequiresPermission(allOf = [
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ])
    private fun getLocationFromLocationManager() {
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isGpsEnabled or isNetworkEnabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES,
                    locationListener)
        }
    }

    private fun getCurrentAddress(context: Context, latitude: Double, longitude: Double) {
        val geocoder = Geocoder(context, Locale.getDefault())
        geocoder.getFromLocation(latitude, longitude, 1)
    }

    private fun startLocationUpdates() {
        /*fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null *//* Looper *//*)*/
    }
}