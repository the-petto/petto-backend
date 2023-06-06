package com.thepetto.core.api.global.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import java.net.InetAddress


@Configuration
@EnableConfigurationProperties(CloudAwsProperties::class)
class CloudAwsConfig(
    val cloudAwsProperties: CloudAwsProperties,
) {

    @Bean
    fun basicAWSCredentials(cloudAwsProperties: CloudAwsProperties): BasicAWSCredentials {
        return BasicAWSCredentials(
            cloudAwsProperties.credentials.accessKey,
            cloudAwsProperties.credentials.secretKey
        )
    }

    @Bean
    fun s3Client(basicAWSCredentials: BasicAWSCredentials): AmazonS3 {
        return AmazonS3Client.builder()
            .withCredentials(AWSStaticCredentialsProvider(basicAWSCredentials))
            .build()
    }

    /**
     * local 개발 환경에서는 클라우드 AWS 대신 로컬에서 docker-compose로 구성한 localstack을 사용합니다.
     */
    @Bean
    @Primary
    @Profile("local")
    fun localS3Client(basicAWSCredentials: BasicAWSCredentials): AmazonS3 {
        val localhost = InetAddress.getLocalHost()
        println("내부 IP 주소: " + localhost.hostAddress)

        return AmazonS3Client.builder()
            .withCredentials(AWSStaticCredentialsProvider(basicAWSCredentials))
            .withPathStyleAccessEnabled(true)
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration(
                    "http://" + localhost.hostAddress + ":4566",
                    cloudAwsProperties.region.static
                )
            )
            .build()
    }
}