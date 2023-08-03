import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.kotlinAndroid)
    id("kotlin-kapt")
}

android {
    //读取本地配置文件
    val localProperties = Properties()
    localProperties.load(rootProject.file("local.properties").inputStream())
    val keystorePath = localProperties.getProperty("keystore.path")
    val keystorePassword = localProperties.getProperty("keystore.password")
    val keystoreAlias = localProperties.getProperty("keystore.alias")
    val keystoreAliasPassword = localProperties.getProperty("keystore.alias_password")

    signingConfigs {
        create("release") {
            storeFile = file(keystorePath)
            storePassword = keystorePassword
            keyAlias = keystoreAlias
            keyPassword = keystoreAliasPassword
        }
    }

    namespace = "tech.foxio.foxlink"
    compileSdk = 34

    defaultConfig {
        applicationId = "tech.foxio.foxlink"
        minSdk = 24
        targetSdk = 34
        versionCode = getVersionCode()
        versionName = getVersionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        //ndk配置
        ndk {
            //noinspection ChromeOsAbiSupport
            abiFilters += listOf("armeabi-v7a", "arm64-v8a")
        }
        //BuildConfig配置
        buildConfigField("String", "DATA_BASE_NAME", "\"AppData.db\"")
        buildConfigField("String", "DATA_STORE_NAME", "\"AppDataStore\"")
        buildConfigField("String", "NOTIFICATION_CHANNEL_ID", "\"FoxLinkChannelID\"")
        buildConfigField("String", "NOTIFICATION_CHANNEL_NAME", "\"FoxLinkConnectState\"")
        buildConfigField("String", "APP_WRITE_PROJECT_ID", "\"64be39e09e155ffc6946\"")
        buildConfigField("String", "APP_WRITE_BASE_URL", "\"https://app.foxio.tech/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    //配置编译后的apk文件名
    applicationVariants.all {
        val buildType = this.buildType.name
        outputs.all {
            if (this is ApkVariantOutputImpl) {
                this.outputFileName =
                    "${rootProject.name}-${versionName}-${versionCode}-${flavorName}-$buildType.apk"
            }
        }
    }
    flavorDimensions += "channel"
    //多渠道打包
    productFlavors {
        //默认
        create("Default") {
            dimension = "channel"
            buildConfigField("String", "APP_CHANNEL", "\"Default\"")
        }
        //GooglePlay
        create("GooglePlay") {
            dimension = "channel"
            buildConfigField("String", "APP_CHANNEL", "\"GooglePlay\"")
        }
        //酷安
        create("CoolApk") {
            dimension = "channel"
            buildConfigField("String", "APP_CHANNEL", "\"CoolApk\"")
        }
    }

    tasks.register("assembleAllChannelsRelease") {
        group = "MyBuild"
        description = "Assembles all release APKs for each channel"
        // 遍历所有渠道的Release构建类型
        val releaseVariants = android.applicationVariants.filter { it.buildType.name == "release" }
        releaseVariants.forEach { variant ->
            variant.productFlavors.forEach { flavor ->
                val formattedFlavor = flavor.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
                val taskName = "assemble${formattedFlavor}Release"
                dependsOn(tasks.getByName(taskName))
            }
        }
    }
}
fun getVersionCode(): Int {
    val majorNumber = 1
    val minorNumber = 0
    val revisionNumber = getRevisionNumber()

    return majorNumber * 1000000 + minorNumber * 10000 + revisionNumber
}

fun getVersionName(): String {
    val majorNumber = 1
    val minorNumber = 0
    val revisionNumber = getRevisionNumber()
    val revisionDescription = getRevisionDescription()

    return "$majorNumber.$minorNumber.$revisionNumber.$revisionDescription"
}

fun getRevisionNumber(): Int {
    val processBuilder = ProcessBuilder("git", "rev-list", "HEAD", "--count")
    val process = processBuilder.start()
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    val result = reader.readLine()?.toIntOrNull() ?: 0
    process.waitFor()
    return result
}

fun getRevisionDescription(): String {
    val processBuilder = ProcessBuilder("git", "describe", "--always")
    val process = processBuilder.start()
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    val result = reader.readLine()?.trim() ?: SimpleDateFormat("yyMMdd").format(Date())
    process.waitFor()
    return if (result.isBlank()) {
        SimpleDateFormat("yyMMdd").format(Date())
    } else {
        result.substring(result.length - 6)
    }
}
dependencies {
    implementation(fileTree("libs"))

    //==================== Logging =================================
    val logVersion = "2.6.9"
    //noinspection UseTomlInstead
    implementation("com.github.fengzhizi715.SAF-Kotlin-log:debug_view:$logVersion")
    //noinspection UseTomlInstead
    implementation("com.github.fengzhizi715.SAF-Kotlin-log:extension:$logVersion")
    //noinspection UseTomlInstead
    implementation("com.github.fengzhizi715.SAF-Kotlin-log:fastjson:$logVersion")
    //noinspection UseTomlInstead
    implementation("com.github.fengzhizi715.SAF-Kotlin-log:okhttp:$logVersion")
    //noinspection UseTomlInstead
    implementation("com.github.fengzhizi715.SAF-Kotlin-log:core:$logVersion")
    //noinspection UseTomlInstead
    implementation("com.github.fengzhizi715.SAF-Kotlin-log:file:$logVersion")
    //noinspection UseTomlInstead
    implementation("com.github.fengzhizi715.SAF-Kotlin-log:gson:$logVersion")

    //==================== Networking =============================
    val retrofitVersion = "2.9.0"
    val okhttpVersion = "4.11.0"
    //noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    //noinspection UseTomlInstead
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")
    //noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    //noinspection UseTomlInstead
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")

    //==================== Dependency Injection ==================
    val hiltVersion = "2.47"
    //noinspection UseTomlInstead
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    //noinspection UseTomlInstead
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    //noinspection UseTomlInstead
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")

    //==================== Database ==============================
    val roomVersion = "2.5.2"
    //noinspection UseTomlInstead
    implementation("androidx.room:room-runtime:$roomVersion")
    //noinspection UseTomlInstead
    implementation("androidx.room:room-ktx:$roomVersion")
    //noinspection UseTomlInstead
    kapt("androidx.room:room-compiler:$roomVersion")

    //==================== Datastore ==============================
    //noinspection UseTomlInstead
    implementation("androidx.datastore:datastore-preferences-core:1.0.0")
    //noinspection UseTomlInstead
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //==================== Pager ==============================
    val pagerVersion = "0.30.1"
    //noinspection UseTomlInstead
    implementation("com.google.accompanist:accompanist-pager-indicators:$pagerVersion")
    //noinspection UseTomlInstead
    implementation("com.google.accompanist:accompanist-pager:$pagerVersion")

    //==================== Memory Leak Detection ====================
    //noinspection UseTomlInstead
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")

    //==================== WebView ============================
    //noinspection UseTomlInstead
    implementation("com.google.accompanist:accompanist-webview:0.30.1")

    //==================== Navigation ===============================
    //noinspection UseTomlInstead
    implementation("androidx.navigation:navigation-compose:2.6.0")

    //==================== AppWrite ============================
    //noinspection UseTomlInstead
    implementation("io.appwrite:sdk-for-android:2.0.0")

    //==================== Image Loading ============================
    //noinspection UseTomlInstead
    implementation("io.coil-kt:coil-compose:2.4.0")

    //==================== Permissions ============================
    //noinspection UseTomlInstead
    implementation("com.github.getActivity:XXPermissions:18.2")

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}
