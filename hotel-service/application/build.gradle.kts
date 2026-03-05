plugins {
    id("java")
}

group = "com.salary-app"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:4.0.2")
    }
}

dependencies {
    // gRPC
//    implementation("io.grpc:grpc-services:1.73.0")
//    implementation("org.springframework.grpc:spring-grpc-spring-boot-starter:$springGrpcVersion")
//    runtimeOnly("io.grpc:grpc-netty-shaded:1.62.2")
//    implementation("com.salary-app:grpc-contracts:1.0.0")


    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(project(":model"))
}

tasks.test {
    useJUnitPlatform()
}