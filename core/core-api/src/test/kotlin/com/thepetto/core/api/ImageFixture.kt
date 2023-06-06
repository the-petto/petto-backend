package com.thepetto.core.api

import org.springframework.core.io.ClassPathResource
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files

private const val imagePath = "image/dog.jpeg"

fun createNormalImageFixture(): MockMultipartFile {
    val resource = ClassPathResource(imagePath)
    val file = resource.file
    val contentType = Files.probeContentType(file.toPath())
    val originalFilename = file.name
    val bytes = Files.readAllBytes(file.toPath())
    return MockMultipartFile(imagePath, originalFilename, contentType, bytes)
}