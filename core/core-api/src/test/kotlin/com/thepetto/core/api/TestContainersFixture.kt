package com.thepetto.core.api

import org.testcontainers.containers.BindMode
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName

fun createLocalstackGenericContainer(): GenericContainer<*> {
    val env = LinkedHashMap<String, String>()
    env["AWS_ACCESS_KEY_ID"] = "accessKey"
    env["AWS_SECRET_ACCESS_KEY"] = "secretKey"
    env["SERVICES"] = "s3"

    return GenericContainer(
        DockerImageName.parse("localstack/localstack:2.1.0")
    )
        .withExposedPorts(4566)
        .withEnv(env)
        .withClasspathResourceMapping(
            "localstack-init",
            "/etc/localstack/init/ready.d",
            BindMode.READ_ONLY
        )
        .waitingFor(Wait.defaultWaitStrategy())
}