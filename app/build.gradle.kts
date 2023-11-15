plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "nick.mirosh.androidsamples"
    compileSdk = 34

    defaultConfig {
        applicationId = "nick.mirosh.androidsamples"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures.buildConfig = true
    buildTypes {
        debug {
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://pokeapi.co/api/v2/\""
            )
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //gson converter factory dependency
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //okhttp dependency
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    //logging interceptor dependency
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    implementation("androidx.navigation:navigation-compose:2.7.5")

    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")
    implementation("androidx.compose.material3:material3:1.2.0-alpha10")
    implementation("androidx.compose.material:material:1.5.4")


    //depdendency for coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("io.coil-kt:coil-compose:2.4.0")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.4")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")
    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    val room_version = "2.6.0"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    implementation("com.github.nsmirosh:ParallaxColumn:1.0.4")
}
kapt {
    correctErrorTypes = true
}

tasks.register("checkManifest") {
    doLast {
        val manifestFile = android.sourceSets["main"].manifest.srcFile
        val manifestContent = manifestFile.readText()
        if (manifestContent.contains("""screenOrientation="portrait"""") ||
            manifestContent.contains("""screenOrientation="landscape"""")
        ) {
            throw GradleException(
                "A screen orientation is locked inside an Activity. " +
                        "This is not allowed."
            )
        }
    }
}

tasks.build {
    dependsOn("checkManifest")
}