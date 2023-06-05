package com.thepetto.core.api

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.thepetto.core.api.global.config.CloudAwsProperties


fun getCloudAwsPropertiesFixture(): CloudAwsProperties = CloudAwsProperties(
    CloudAwsProperties.Credentials(
        "accessKey",
        "secretKey",
    ),
    CloudAwsProperties.Region("ap-northeast-2"),
    CloudAwsProperties.Stack(false),
    CloudAwsProperties.S3("test"),
)

fun getS3ClientFixture(endpoint: String, cloudAwsProperties: CloudAwsProperties): AmazonS3 = AmazonS3Client.builder()
    .withEndpointConfiguration(
        AwsClientBuilder.EndpointConfiguration(
            endpoint,
            cloudAwsProperties.region.static,
        )
    )
    .withPathStyleAccessEnabled(true)
    .withCredentials(
        AWSStaticCredentialsProvider(
            BasicAWSCredentials(
                cloudAwsProperties.credentials.accessKey,
                cloudAwsProperties.credentials.secretKey,
            )
        )
    )
    .build()
