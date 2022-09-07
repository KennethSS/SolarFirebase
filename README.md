# SolarFirebase

### Gradle

## FCM


```gradle
dependencies {
    // Firebase
    implementation 'com.google.firebase:firebase-database:19.3.0'
    implementation 'com.google.firebase:firebase-auth:19.3.1'

    // Firebase UI
    implementation 'com.firebaseui:firebase-ui-auth:4.3.1'
    implementation 'com.firebaseui:firebase-ui-database:6.2.1'

}
```



## Crashlytics

- 오류 보고서 맞춤 설정

- 명료한 오류 보고서 받기

- 신속 알림 맞춤 설정

  

### Enable opt-in reporting

```xml
<meta-data
    android:name="firebase_crashlytics_collection_enabled"
    android:value="false" />
```

```kotlin
FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
```



### 명료한 오류 보고서 받기

```
-keepattributes SourceFile,LineNumberTable        # Keep file names and line numbers.
-keep public class * extends java.lang.Exception  # Optional: Keep custom exceptions.
```



#### 난독화된 빌드 변형 유지

Crashlytics Gradle 플러그인이 난독화를 사용하는 변형의 매핑 파일을 업로드하지 않도록 하려면 앱 수준 `build.gradle` 파일에서 `firebaseCrashlytics.enableMappingFileUpload` Gradle 확장 속성을 `false`로 설정하세요. 이렇게 하면 난독화된 빌드의 빌드 시간을 단축할 수 있지만 생성된 스택 트레이스는 Firebase Console의 Crashlytics 페이지에 난독화되어 표시됩니다.

```
android {

  // To enable Crashlytics mapping file upload for specific build types:
  buildTypes {
    debug {
      minifyEnabled true
      firebaseCrashlytics {
        mappingFileUploadEnabled false
      }
    }
  }

  ...

  // To enable Crashlytics mapping file upload for specific product flavors:
  flavorDimensions "environment"
  productFlavors {
    staging {
      dimension "environment"
      ...
      firebaseCrashlytics {
        mappingFileUploadEnabled false
      }
    }
    prod {
      dimension "environment"
      ...
      firebaseCrashlytics {
        mappingFileUploadEnabled true
      }
    }
  }
}
```





# FireStore

- FireStore에서 ArrayList를 Set하면 list의 필드이름으로 아래 Value들이 들어감
- FireStore에 Set을 할때 Any타입을 받지만 Map이나 커스텀 객체 밖에 안되는듯
- 데이터를 toObject로 불러올 때는 Default 값이 설정되어 있어야함