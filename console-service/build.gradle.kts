import com.github.gradle.node.npm.task.NpmTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    id("com.github.node-gradle.node") version "7.0.2"
}

group = "zm.gov.moh"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

springBoot {
    mainClass = "zm.gov.moh.consoleservice.ConsoleServiceApplication"
}

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

extra["springCloudVersion"] = "2022.0.4"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("io.zipkin.reporter2:zipkin-reporter-brave")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    testImplementation("io.projectreactor:reactor-test")
    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.109.Final")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

node {
    download = true
    version = "21.6.2"
}

tasks.register("npmBuild", NpmTask::class.java) {
    dependsOn(tasks.npmInstall)
    group = "Custom"
    description = "Builds front-end app."
    workingDir = file("${project.projectDir}/src/frontend")
    args = listOf("run", "build")
}

tasks.register("copyWebApp") {
    dependsOn("npmBuild")
    group = "Custom"
    description = "Copy web app to into the service static content."

    copy {
        from("${project.projectDir}/src/frontend/build")
        into("${project.projectDir}/build/resources/main/static/.")
    }
}

tasks.bootJar.configure {
    dependsOn("copyWebApp")
}


