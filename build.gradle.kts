plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Needed for Glide's annotation processor
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.pokemonapiapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.pokemonapiapp"
        minSdk = 27
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Existing catalog dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // --- Added for Project 5 ---

    // CodePath AsyncHttpClient (for API calls)
    implementation("com.codepath.libraries:asynchttpclient:2.2.0")

    // Glide (for loading Pokémon sprite images)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")
}
