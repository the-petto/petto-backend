package com.thepetto.core.api.global.util

import com.amazonaws.services.s3.AmazonS3
import com.thepetto.core.api.createLocalstackGenericContainer
import com.thepetto.core.api.createNormalImageFixture
import com.thepetto.core.api.getCloudAwsPropertiesFixture
import com.thepetto.core.api.getS3ClientFixture
import com.thepetto.core.api.global.config.CloudAwsProperties
import io.kotest.core.extensions.install
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.testcontainers.TestContainerExtension
import io.kotest.extensions.testcontainers.perTest
import io.kotest.matchers.shouldNotBe


class S3UploaderIntegrationTest : BehaviorSpec({
    val localstack = createLocalstackGenericContainer()
    val localstackContainer = install(TestContainerExtension(localstack))
    listener(localstackContainer.perTest())
    val localstackEndpoint = "http://" + localstackContainer.host + ":" + localstackContainer.getMappedPort(4566)

    var s3Client: AmazonS3? = null
    var cloudAwsProperties: CloudAwsProperties? = null

    beforeTest {
        cloudAwsProperties = getCloudAwsPropertiesFixture()
        s3Client = getS3ClientFixture(localstackEndpoint, cloudAwsProperties!!)
    }

    Given("업로드 대상 파일이 주어졌을 때") {
        val imageFile = createNormalImageFixture()

        val s3Uploader = S3Uploader(s3Client!!, cloudAwsProperties!!)
        Thread.sleep(5000)
        When("S3에 파일 업로드를 성공하면") {
            val path = s3Uploader.upload(imageFile, "test")
            Then("업로드 후 s3 경로를 반환한다.") {
                path shouldNotBe null
                println(path)
            }
        }
    }
})
