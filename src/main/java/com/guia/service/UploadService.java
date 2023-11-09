package com.guia.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String fazerUpload(MultipartFile file);
}
