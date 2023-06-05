package com.thepetto.core.api.global.util

import com.amazonaws.AmazonServiceException
import com.amazonaws.SdkClientException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.thepetto.core.api.global.config.CloudAwsProperties
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*

@Component
class S3Uploader(
    private val s3Client: AmazonS3,
    private val cloudAwsProperties: CloudAwsProperties,
) {

    fun upload(uploadFile: MultipartFile, dirName: String): String? {
        val originalFileName: String = uploadFile.originalFilename
            ?: throw RuntimeException()

        val fileType = originalFileName.substring(originalFileName.lastIndexOf("."));
        val randomFullName = UUID.randomUUID().toString() + fileType

        val fileName = "$dirName/$randomFullName"

        return putS3(uploadFile, fileName)
    }

    /**
     * key는 경로이다. 파일이름 풀로 적는다. ex) static/test.txt
     */
    fun deleteFileFromS3(key: String) {
        deleteFile(key)
    }

    private fun putS3(uploadFile: MultipartFile, fileName: String): String {
        val metadata = ObjectMetadata()
        metadata.contentType = uploadFile.contentType
        metadata.contentLength = uploadFile.size

        val bucket = cloudAwsProperties.s3.bucket

        try {
            uploadFile.inputStream.use { stream ->
                s3Client.putObject(
                    PutObjectRequest(bucket, fileName, stream, metadata)
                        .withCannedAcl(
                            CannedAccessControlList
                                .PublicRead
                        )
                )
            }
        } catch (e: IOException) {
            // TODO: handle exception
            e.printStackTrace()
        }

        return s3Client.getUrl(bucket, fileName)
            .toString()
    }

    private fun deleteFile(key: String) {
        val bucket = cloudAwsProperties.s3.bucket
        val deleteObjectRequest = DeleteObjectRequest(bucket, key)
        try {
            s3Client.deleteObject(deleteObjectRequest)
        } catch (e: AmazonServiceException) {
            // TODO: handle exception
            e.printStackTrace()
        } catch (e: SdkClientException) {
            // TODO: handle exception
            e.printStackTrace()
        }
    }
}