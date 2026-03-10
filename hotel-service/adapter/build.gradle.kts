val springGrpcVersion = "1.0.2"

plugins {
    id("java")
    id("com.google.protobuf") version "0.9.5"
}

group = "com.salary-app"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:4.0.2")
        mavenBom("org.springframework.grpc:spring-grpc-dependencies:$springGrpcVersion")
        mavenBom("io.grpc:grpc-bom:1.73.0")
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.34.0"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.73.0"
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
    // REDIS
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    testImplementation("org.springframework.boot:spring-boot-starter-data-redis-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    
    // gRPC
    implementation("org.springframework.grpc:spring-grpc-spring-boot-starter:$springGrpcVersion")
    implementation("com.salary-app:grpc-contracts:1.0.0")

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Kafka
    implementation("org.springframework.boot:spring-boot-starter-kafka")
    implementation("io.projectreactor.kafka:reactor-kafka:1.3.25")

    // Model & Application
    implementation(project(":model"))
    implementation(project(":application"))

    // Lombok + MapStruct
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    // Tests
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-kafka-test")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
