plugins {
    id("java")
    id("org.springframework.boot") version "4.0.2" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    id("org.hidetake.swagger.generator") version "2.19.2"
}

group = "com.salary-app"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

subprojects {
    apply(plugin = "java")

    if (project.name == "bootstrap") {
        apply(plugin = "org.springframework.boot")
    }

    apply(plugin = "io.spring.dependency-management")

    group = "com.salary-app"
    version = "1.0.0"


    repositories {
        mavenCentral()
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    dependencies {
        implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:3.0.1")
        testImplementation(platform("org.junit:junit-bom:5.11.3"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        testImplementation("org.assertj:assertj-core:3.26.3")
    }

    tasks.test {
        useJUnitPlatform()
    }
    tasks.withType(JavaCompile::class) {
        options.compilerArgs.add("-parameters")
    }
}
