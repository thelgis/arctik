import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  val kotlinVersion = "1.3.30" // TODO there is 1.3.31

  kotlin("jvm") version kotlinVersion
}

val ktorVersion = "1.1.3" // TODO there is 1.2.1 https://ktor.io/quickstart/migration/1.2.0.html

group = "com.thelgis"
version = "0.0.1-SNAPSHOT"

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