plugins {
    java
    id("org.springframework.boot") version "4.0.2" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
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
        testImplementation(platform("org.junit:junit-bom:5.11.3"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        testImplementation("org.assertj:assertj-core:3.26.3")
    }

    tasks.test {
        useJUnitPlatform()
    }
}

