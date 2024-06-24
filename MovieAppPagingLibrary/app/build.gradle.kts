plugins {
    alias(libs.plugins.android.application)
    id("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.ammar.movieapppaginglibrary"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.ammar.movieapppaginglibrary"
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

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.converter.gson)

    //RxJava3 with Retrofit
    implementation(libs.adapter.rxjava3)


    // Paging
    val paging_version = "3.3.0"

    implementation("androidx.paging:paging-runtime:$paging_version")
    // optional - RxJava3 support
    implementation("androidx.paging:paging-rxjava3:$paging_version")


    // Dagger Hilt
    implementation ("com.google.dagger:hilt-android:2.51.1")
    annotationProcessor ("com.google.dagger:hilt-compiler:2.51.1")

    // Glide for image loading
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // Lifecycle
    val lifecycle_version = "2.8.2"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")

}




