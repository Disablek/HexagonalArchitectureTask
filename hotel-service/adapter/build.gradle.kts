val springGrpcVersion = "1.0.2"

plugins {
    id("java")
    id("com.google.protobuf") version "0.9.5"
}

group = "com.salary-app"
version = "unspecified"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:4.0.2")
        mavenBom("org.springframework.grpc:spring-grpc-dependencies:$springGrpcVersion")
        mavenBom("io.grpc:grpc-bom:1.62.2")
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.25.5"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.62.2"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("grpc") {
                    option("@generated=omit")
                }
            }
        }
    }
}

dependencies {
    // gRPC
    implementation("io.grpc:grpc-services:1.73.0")
    implementation("org.springframework.grpc:spring-grpc-spring-boot-starter:$springGrpcVersion")
    runtimeOnly("io.grpc:grpc-netty-shaded:1.62.2")
    implementation("com.salary-app:grpc-contracts:1.0.0")

    implementation("org.projectlombok:lombok")
    implementation("org.mapstruct:mapstruct:1.6.3")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")

    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    implementation("org.springframework.boot:spring-boot-starter-liquibase")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    //implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(project(":application"))
    implementation(project(":model"))
}

tasks.test {
    useJUnitPlatform()
}