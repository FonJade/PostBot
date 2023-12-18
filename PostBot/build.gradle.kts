import java.util.regex.Pattern.compile

plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:24.0.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.telegram:telegrambots:6.8.0")
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("com.vk.api:sdk:1.0.14")
    implementation ("org.xerial:sqlite-jdbc:3.42.0.0")
    implementation("com.konghq:unirest-java:3.13.0")
    implementation("com.github.aquality-automation:aquality-selenium:3.0.0")
    implementation("club.minnced:opus-java:1.1.1")
    implementation("net.dv8tion:JDA:5.0.0-beta.18")

}

tasks.test {
    useJUnitPlatform()
}