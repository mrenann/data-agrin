import io.gitlab.arturbosch.detekt.Detekt
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

allprojects {
    detekt {
        toolVersion = libs.versions.detekt.get()
        config.setFrom(file("$rootDir/config/detekt/detekt.yml"))
    }
    tasks.withType<Detekt>().configureEach {
        reports {
            xml.required.set(true)
            html.required.set(false)
            txt.required.set(false)
        }
    }
    tasks.withType<Detekt>().configureEach {
        reports {
            xml.outputLocation.set(file("$rootDir/reports/detekt.xml"))
        }
    }
}

val apiPropertiesFile = rootProject.file("api.properties")
val apiProperties = Properties()
if (apiPropertiesFile.exists()) apiProperties.load(apiPropertiesFile.inputStream())

android {
    namespace = "com.mrenann.dataagrin"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.mrenann.dataagrin"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "${apiProperties["URL"]}")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

ktlint {
    android = true
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.SARIF)
        reporter(ReporterType.CHECKSTYLE)
    }
}


kover {
    reports {
        total {
            xml {
                onCheck = true
            }
        }

        verify {
            rule {
                minBound(50)
            }
        }

        filters {
            excludes {
                annotatedBy(
                    "androidx.compose.runtime.Composable",
                    "androidx.compose.ui.tooling.preview.Preview"
                )
                packages(
                    "com.mrenann.dataagrin.*.presennnntation.screens*",
                    "com.mrenann.dataagrin.*.presennnntation.tab*",
                    "com.mrenann.dataagrin.*.presentation.components*",
                    "com.mrenann.dataagrin.core.ui.theme",
                    "com.mrenann.dataagrin.*.di"
                )
            }
        }
        total {
            xml {
                onCheck = true
            }

            html {
                onCheck = true
            }
        }
    }

}

ksp {
    arg("lyricist.internalVisibility", "true")
    arg("lyricist.generateStringsProperty", "true")
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.bundles.voyager)
    implementation(libs.lyricist)
    ksp(libs.lyricist.processor)
    implementation(libs.composeIcons.evaIcons)
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    // TESTS
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.androidx.core)
    testImplementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}