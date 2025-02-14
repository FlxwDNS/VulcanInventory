plugins {
    id("java")
    id("maven-publish")
}

group = "de.flxwdev"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

publishing {
    repositories {
        maven {
            name = "flxwdevRepository"
            url = uri("https://repo.flxwdev.de/releases")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "dev.flxwdev"
            artifactId = "vulcan-inventory"
            version = this.version
            from(components["java"])
        }
    }
}