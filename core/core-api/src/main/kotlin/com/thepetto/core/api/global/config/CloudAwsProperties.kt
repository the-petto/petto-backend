package com.thepetto.core.api.global.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cloud.aws")
data class CloudAwsProperties(
    val credentials: Credentials,
    val region: Region,
    val stack: Stack,
    val s3: S3,
) {

    data class Credentials(
        val accessKey: String,
        val secretKey: String,
    )

    data class Region(
        val static: String,
    )

    data class Stack(
        val auto: Boolean,
    )

    data class S3(
        val bucket: String,
    )
}