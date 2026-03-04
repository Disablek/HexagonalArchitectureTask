val springCloudVersion by extra("2025.1.0")
val springGrpcVersion by extra("1.0.2")
plugins {
    id("java")
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:4.0.2")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
        mavenBom("org.springframework.grpc:spring-grpc-dependencies:$springGrpcVersion")
    }
}

group = "com.salary-app"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.grpc:spring-grpc-spring-boot-starter")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(project(":model"))
    implementation(project(":application"))
    implementation(project(":adapter"))
    runtimeOnly("org.postgresql:postgresql")
}

tasks.test {
    useJUnitPlatform()
}