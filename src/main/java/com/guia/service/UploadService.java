package com.guia.service;

import org.springframework.web.multipart.MultipartFile;

/*
 * Essa Interface é uma boa prática para não mostrar os métodos do sistema.
 * Os métodos estão na implementação do serviço, separado.
 */
public interface UploadService {
    String fazerUpload(MultipartFile file);
}
