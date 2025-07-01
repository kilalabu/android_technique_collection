import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    kotlin("kapt")
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.roborazzi)
    kotlin("plugin.power-assert") version libs.versions.kotlin
}

android {
    namespace = "com.example.android_technique_collection"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.android_technique_collection"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            all {
                it.systemProperties(
                    "roborazzi.output.dir" to rootProject.file("screenshots").absolutePath,
                    "robolectric.pixelCopyRenderMode" to "hardware"
                )
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material2)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.coil)

    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.roborazzi)
    testImplementation(libs.roborazzi.compose)
    testImplementation(libs.roborazzi.rule)
    testImplementation(libs.roborazzi.compose.preview.scanner)
    testImplementation(libs.compose.preview.scanner)
    testImplementation(libs.androidx.espresso.core)
    testImplementation(libs.androidx.ui.test.junit4)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// Hilt: Allow references to generated code
kapt {
    correctErrorTypes = true
}

@OptIn(ExperimentalRoborazziApi::class)
roborazzi {
    outputDir.set(rootProject.file("screenshots"))
//    compare.outputDir.set(rootProject.file("screenshots/compare"))
    generateComposePreviewRobolectricTests {
        enable = true
        // The package names to scan for Composable Previews.
        packages = listOf("com.example.android_technique_collection.feature")
        // robolectricConfig will be passed to Robolectric's @Config annotation in the generated test class.
        // See https://robolectric.org/configuring/ for more information.
//        robolectricConfig = mapOf(
//            "sdk" to "[32]",
//            "qualifiers" to "RobolectricDeviceQualifiers.Pixel5",
//        )
        // The fully qualified class name of the custom test class that implements [com.github.takahirom.roborazzi.ComposePreviewTester].
        testerQualifiedClassName = "com.example.android_technique_collection.MyComposePreviewTester"
    }
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
powerAssert {
    functions = listOf("kotlin.assert", "kotlin.require", "kotlin.test.assertTrue", "kotlin.test.assertEquals", "kotlin.test.assertNull")
    includedSourceSets = listOf("debugUnitTest")
}