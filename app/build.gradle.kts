plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "nick.mirosh.androidsamples"
    compileSdk = 35

    defaultConfig {
        applicationId = "nick.mirosh.androidsamples"
        minSdk = 24
        targetSdk = 35
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

            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://pokeapi.co/api/v2/\""
            )

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        languageVersion = "1.9"
    }
    kotlin.sourceSets.all {
        this.languageSettings.enableLanguageFeature("DataObjects")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2024.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")

    val retrofit = "2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    //gson converter factory dependency
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("androidx.navigation:navigation-compose:2.8.3")

    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0")
    val hilt = "2.52"
    implementation("com.google.dagger:hilt-android:$hilt")
    kapt("com.google.dagger:hilt-android-compiler:$hilt")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.material:material:1.7.5")


    implementation ("com.airbnb.android:lottie-compose:6.4.1")

    val coroutines = "1.9.0"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines")

    // ViewModel
    val lifecycle = "2.8.7"

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle")

    implementation("io.coil-kt:coil-compose:2.6.0")

    val uiTest = "1.7.5"
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$uiTest")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$uiTest")
    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

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