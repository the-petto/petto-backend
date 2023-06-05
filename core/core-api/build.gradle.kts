dependencies {
    implementation(project(":core:core-enum"))
    implementation(project(":support:swagger"))
    implementation(project(":support:kotest"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    implementation("org.flywaydb:flyway-core:8.5.5")

    implementation("io.awspring.cloud:spring-cloud-starter-aws:2.4.4")

    testImplementation("org.testcontainers:testcontainers:1.18.3")
    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:1.3.4")
}