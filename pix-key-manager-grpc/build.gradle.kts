import com.google.protobuf.gradle.*
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.21"
    id("org.jetbrains.kotlin.kapt") version "1.5.21"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("io.micronaut.application") version "2.0.6"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.5.21"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.5.21"
    id("com.google.protobuf") version "0.8.15"
}


version = "0.1"
group = "br.com.zupacademy.apass"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

micronaut {
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("br.com.zupacademy.apass.pix.keymanagergrpc.*")
    }
}

dependencies {
    kapt("io.micronaut.data:micronaut-data-processor")

    kapt("io.micronaut:micronaut-http-validation")

    annotationProcessor("io.micronaut:micronaut-validation")

    implementation("io.micronaut:micronaut-http-client")

    implementation("io.micronaut:micronaut-runtime")

    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")

    implementation("io.micronaut.beanvalidation:micronaut-hibernate-validator")

    implementation("org.hibernate:hibernate-validator:6.0.2.Final")

    implementation("io.micronaut.sql:micronaut-jdbc-hikari")

    implementation("io.micronaut.grpc:micronaut-grpc-server-runtime:3.0.0")

    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")

    implementation("io.micronaut.xml:micronaut-jackson-xml")

    implementation("javax.annotation:javax.annotation-api")

    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")

    runtimeOnly("ch.qos.logback:logback-classic")

    runtimeOnly("org.postgresql:postgresql")

    runtimeOnly("com.h2database:h2")

    testImplementation("org.testcontainers:junit-jupiter")

    testImplementation("org.testcontainers:postgresql")

    testImplementation("org.testcontainers:testcontainers")

    implementation("io.micronaut:micronaut-validation")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("io.micronaut:micronaut-http-client")

}


application {
    mainClass.set("br.com.zupacademy.apass.pix.keymanagergrpc.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }


}
sourceSets {
    main {
        java {
            srcDirs("build/generated/source/proto/main/grpc")
            srcDirs("build/generated/source/proto/main/java")
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.17.2"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.38.0"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                // Apply the "grpc" plugin whose spec is defined above, without options.
                id("grpc")
            }
        }
    }
}
