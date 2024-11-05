plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.noarg") version "2.0.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.sql2o:sql2o:1.8.0")
    implementation("com.h2database:h2:2.3.230")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
noArg {
    annotations("org.example.NoArg")
}