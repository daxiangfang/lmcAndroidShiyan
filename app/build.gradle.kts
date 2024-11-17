plugins {
    alias(libs.plugins.android.application)
}

android {
<<<<<<< HEAD
    namespace = "com.example.myapplication0000"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication0000"
=======
    namespace = "com.example.my20241app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.my20241app"
>>>>>>> 8bd7eba4e5fd8abdda694afe3c2dda15b6417395
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}