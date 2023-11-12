package com.guia.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.guia.service.UploadService;
import com.guia.service.exception.BusinessException;

@Service
public class UploadServiceImpl implements UploadService {
    @Value("${upload.directory}")
    private String uploadDirectory;

    @Value("${upload.link}")
    private String link;

    @Transactional
    public String fazerUpload(MultipartFile file) {
        try {
            byte[] logomarca = file.getBytes();
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();

            try (OutputStream os = new FileOutputStream(filePath)) {
                os.write(logomarca);
            }

            String linkDaImagem = link + "/img/" + fileName; // O caminho do link da imagem no Nginx
            return linkDaImagem;
        } catch (IOException e) {
            throw new BusinessException("Erro ao fazer o upload da imagem.");
        }
    }
}
