package com.enigma.challengetokonyadiaapi.service;

import com.enigma.challengetokonyadiaapi.entity.Product;
import com.enigma.challengetokonyadiaapi.entity.ProductImage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ProductImageService {
    ProductImage createImage(MultipartFile multipartFile);
    Resource findByPath(String path);
    void deleteFile(String path);
    Resource getMenuImageById(String id);
    void delete(String id);
}
