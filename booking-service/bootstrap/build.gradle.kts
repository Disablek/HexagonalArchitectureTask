val springCloudVersion by extra("2025.1.0")

plugins {
    id("java")
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
}
extra["springGrpcVersion"] = "1.0.2"

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:4.0.2")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
        mavenBom("org.springframework.grpc:spring-grpc-dependencies:${property("springGrpcVersion")}")

    }
}

group = "com.salary-app"
version = "unspecified"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.grpc:grpc-services")
    implementation("org.springframework.grpc:spring-grpc-client-spring-boot-starter")
    implementation("org.springframework.grpc:spring-grpc-server-spring-boot-starter")
    implementation("com.salary-app:grpc-contracts:1.0.0")
    runtimeOnly("io.grpc:grpc-netty:1.73.0")
    runtimeOnly("io.grpc:grpc-netty-shaded:1.73.0")

    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-r2dbc")

    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.springframework.boot:spring-boot-starter-liquibase")

    implementation(project(":model"))
    implementation(project(":application"))
    implementation(project(":adapter"))
    implementation("org.postgresql:r2dbc-postgresql:1.1.1.RELEASE")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
