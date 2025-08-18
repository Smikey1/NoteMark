import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "com.twugteam.admin.notemark"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.twugteam.admin.notemark"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(FileInputStream(localPropertiesFile))
    }

    buildTypes {
        debug {
            buildConfigField("String","NOTEMARK_API_BASE_URL","\"${localProperties.getProperty("NOTEMARK_API_BASE_URL")}\"")
            buildConfigField("String","AUTH_ENDPOINT","\"${localProperties.getProperty("AUTH_ENDPOINT")}\"")
            buildConfigField("String","NOTES_ENDPOINT","\"${localProperties.getProperty("NOTES_ENDPOINT")}\"")
            buildConfigField("String","EMAIL","\"${localProperties.getProperty("EMAIL")}\"")
            buildConfigField("String","DELETE_ENDPOINT","\"${localProperties.getProperty("DELETE_ENDPOINT")}\"")
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }
}


dependencies {

    // default Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.kotlinx.serialization.json)

    // Koin
    implementation(libs.bundles.koin.compose)

    //Adaptive Layout
    implementation(libs.androidx.material3.window.size.class1)

    // Splashscreen & Widget
    implementation(libs.androidx.core.splashscreen)

    // Database - Room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)


    // Allow use of java.time.Instant below API 26
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // Logging
    implementation(libs.timber)

    // ktor
    implementation(libs.bundles.ktor)

    //Preference DataStore
    implementation(libs.androidx.datastore.preferences)

    //WorkManager
    implementation(libs.androidx.work.runtime.ktx.v281)

    //core is needed to use those in WorkerUtils
    //ActivityCompat NotificationCompat NotificationManagerCompat
    implementation (libs.androidx.core)

    //paging with room
    implementation(libs.androidx.room.paging)

    //Paging 3
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
}