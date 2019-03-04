import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  val kotlinVersion = "1.3.21"

  kotlin("jvm") version kotlinVersion
}

val ktorVersion = "1.1.3"

group = "com.thelgis"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  jcenter()
  maven("https://kotlin.bintray.com/kotlinx")
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  
  implementation("io.ktor:ktor-client:$ktorVersion")
  implementation("io.ktor:ktor-client-apache:$ktorVersion")
  implementation("io.ktor:ktor-client-json:$ktorVersion")
  implementation("io.ktor:ktor-client-gson:$ktorVersion")
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}