plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.finalapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.finalapplication"
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

    viewBinding {
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))

    // email login
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")

    // MultiDex
    implementation("androidx.multidex:multidex:2.0.1")

    // google login
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    // retrofit2
    implementation("com.squareup.retrofit2:retrofit:2.0.0")

    // gson
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // tikxml
    implementation("com.tickaroo.tikxml:annotation:0.8.13")
    implementation("com.tickaroo.tikxml:core:0.8.13")
    implementation("com.tickaroo.tikxml:retrofit-converter:0.8.13")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt ("com.tickaroo.tikxml:processor:0.8.13")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")

    // preference
    implementation("androidx.preference:preference:1.2.1")

    // firebase
    implementation("com.google.firebase:firebase-firestore-ktx:25.0.0")

    // Storage
    implementation("com.google.firebase:firebase-storage-ktx:21.0.0")

    // kakao
    implementation("com.kakao.sdk:v2-user:2.12.1")
}