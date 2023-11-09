package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.entity.Product;
import com.enigma.challengetokonyadiaapi.entity.ProductImage;
import com.enigma.challengetokonyadiaapi.repository.ProductImageRepository;
import com.enigma.challengetokonyadiaapi.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final Path derectoryPath = Paths.get("E:/spring-boot-project/images");

    @Override
    public ProductImage createImage(MultipartFile multipartFile) {
        try{
            if (multipartFile.isEmpty())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad Rquest ");
            if(!Files.exists(derectoryPath)){
                Files.createDirectory(derectoryPath);
            }
            String fileName =String.format("%d_%s",System.currentTimeMillis(),multipartFile.getOriginalFilename());

            Path filePath =  derectoryPath.resolve(fileName);
            Files.copy(multipartFile.getInputStream(),filePath);

            ProductImage productImage = ProductImage.builder()
                    .size(multipartFile.getSize())
                    .contentType(multipartFile.getContentType())
                    .name(fileName)
                    .path(filePath.toString())
                    .build();
            return  productImageRepository.saveAndFlush(productImage);

        }catch (IOException e) {
//            throw new RuntimeException(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"a");
        }
    }

    @Override
    public Resource findByPath(String path) {
        try {
            Path filepath = Paths.get(path);
            return new UrlResource(filepath.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(String path) {
        try{
         Path filePath = Paths.get(path);
         Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Resource getMenuImageById(String id) {
        ProductImage productImage = productImageRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"image not found")
        );
        return findByPath(productImage.getPath());
    }

    @Override
    public void delete(String id) {
        try{
            ProductImage productImage = productImageRepository.findById(id).orElseThrow(
                    ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"image not found")
            );
            productImageRepository.delete(productImage);
            Path filePath = Paths.get(productImage.getPath());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
