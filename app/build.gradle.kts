plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    alias(libs.plugins.daggerHilt)
    id("com.google.gms.google-services")
    id ("com.google.firebase.firebase-perf")
}

android {
    namespace = "com.listing.movie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.listing.movie"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }




    flavorDimensions += "version"
    productFlavors {



    }



    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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
    implementation(project(":domain"))
    implementation(project(":di"))
    testImplementation(libs.junit)
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.bumptech.glide)
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter)
    implementation(libs.datastore)


    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))

    val firebaseVersion = "21.1.1"
    implementation("com.google.firebase:firebase-core:$firebaseVersion")
    implementation("com.google.firebase:firebase-analytics:$firebaseVersion")
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-perf-license:12.0.1")
    implementation("com.google.firebase:perf-plugin:1.4.2")

    // Dagger Hilt
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.hilt.android)

    // ViewModel
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    
    implementation(libs.worker)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.androidx.core.testing.v210)


}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
