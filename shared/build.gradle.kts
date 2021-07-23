import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin(KotlinPlugins.multiplatform)
    kotlin(KotlinPlugins.cocoapods)
    kotlin(KotlinPlugins.serialization) version "1.5.0"
    id(Plugins.androidLibrary)
    id(Plugins.realm) version Realm.version
    id(Plugins.kmpNativeCoroutine) version "0.4.2"
    id(Plugins.koin)
}

version = "1.0"

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {}

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        frameworkName = "KotlinCore"
        podfile = project.file("../iosApp/Podfile")
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation(Realm.realm)
                implementation(Koin.core)

                with(Ktor) {
                    implementation(core)
                    implementation(clientSerialization)
                    implementation(logging)
                }

                with(Kotlinx) {
                    implementation(serialization)
                    implementation(coroutine) {
                        version {
                            strictly(coroutineVersion)
                        }
                    }
                }
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Ktor.android)
                implementation(Koin.android)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Ktor.ios)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(16)
        targetSdkVersion(30)
    }
}