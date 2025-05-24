import org.gradle.kotlin.dsl.implementation

plugins {
    id("java")
}

group = "com.miti99"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val discord4jVersion = "3.2+"
val junitVersion = "5.10.0"
dependencies {
    implementation("com.discord4j:discord4j-core:$discord4jVersion")

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
