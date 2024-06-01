plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.investhub"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.investhub"
        minSdk = 24
        targetSdk = 33
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
    buildFeatures {
        viewBinding=true
        dataBinding=true
        buildConfig=true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {


    // Glide
    implementation("com.github.bumptech.glide:glide:4.14.2")
// RecycleView
    implementation("androidx.recyclerview:recyclerview:1.2.0")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("androidx.databinding:viewbinding:8.1.3")
    implementation("com.google.firebase:firebase-firestore:24.10.1")
    implementation("com.google.firebase:firebase-database-ktx:20.3.0")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation("com.google.firebase:firebase-storage:20.3.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // Navigation Graph
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.2")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.2")

    //Responsive layout
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    //Responsive text
    implementation("com.intuit.ssp:ssp-android:1.1.0")

    //Country Picker
    implementation("com.hbb20:ccp:2.7.3")

    //OtpView
    implementation("io.github.chaosleung:pinview:1.4.4")

    // CircleImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    //Fresco
    implementation("com.facebook.fresco:fresco:2.6.0")

    // Import the BoM for the Firebase platform

    implementation("com.google.firebase:firebase-auth")
    // Also add the dependency for the Google Play services library and specify its version
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    //Firebase realtime Database
    implementation("com.google.firebase:firebase-database")
    //firebase messaging
    implementation("com.google.firebase:firebase-messaging:23.4.1")


    //Okhttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    //Swipe to refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")





}