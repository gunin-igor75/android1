plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.firebase)
    alias(libs.plugins.crashlytics)
}

android {
    namespace = "ru.it_cron.intern1"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.it_cron.intern1"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.lifecycle.viewmodel)

    //data store
    implementation(libs.datastore.core)

    //koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    //cicerone
    implementation(libs.cicerone.core)

    //livedata
    implementation(libs.liveData.core)

    //okhttp
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)

    //retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gsonConverter)

    //intuit
    implementation(libs.intuit.spd)
    implementation(libs.intuit.ssp)

    //glide
    implementation(libs.glide)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.analytics)
    implementation(libs.crashlytics)

    testImplementation(libs.junit)
    testImplementation(libs.assertj.core)
    testImplementation(libs.arch.core)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}