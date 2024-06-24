// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    kotlin("kapt") version "2.0.0"
    // Dagger Hilt
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}