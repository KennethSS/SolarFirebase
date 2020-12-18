package com.solar.firebase.remoteconfig

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.solar.firebase.R

/**
 *  Created by Kenneth on 2020/11/30
 */
object RemoteConfigManager {
    /**
     *  이 액션을 해주어야 파이어베이스 콘솔과 동기화가됨
     */
    fun fetchAndActiveToResult(result: (remote: FirebaseRemoteConfig) -> Unit) {
        val settings = FirebaseRemoteConfigSettings.Builder()
                .setFetchTimeoutInSeconds(3) // Fetch 타임아웃을 줘서 UX에 악영향이 없도록
                .setMinimumFetchIntervalInSeconds(3600 * 24) // 24시간 - 자주 호출을 막기 위함 캐시 갱신을 하루에 걸쳐 진행
                .build()

        val remoteConfig = FirebaseRemoteConfig.getInstance()

        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults) // Default 값을 xml 로 정의 가능
        remoteConfig.setConfigSettingsAsync(settings)
        remoteConfig.fetchAndActivate()
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        // fetchSuccess 후 value 반환
                        Log.d(TAG, remoteConfig.getString("app_version"))
                        result(remoteConfig)
                    } else {
                        Log.d(TAG, "Fetch failed")
                    }
                }
    }

    private const val TAG = "RemoteConfigManager"
}