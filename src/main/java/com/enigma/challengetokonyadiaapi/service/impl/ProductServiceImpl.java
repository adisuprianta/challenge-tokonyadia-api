package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.dto.request.ProductRequest;
import com.enigma.challengetokonyadiaapi.dto.response.FileResponse;
import com.enigma.challengetokonyadiaapi.dto.response.ProductResponse;
import com.enigma.challengetokonyadiaapi.dto.response.StoreResponse;
import com.enigma.challengetokonyadiaapi.entity.Product;
import com.enigma.challengetokonyadiaapi.entity.ProductImage;
import com.enigma.challengetokonyadiaapi.entity.Store;
import com.enigma.challengetokonyadiaapi.repository.ProductRepository;
import com.enigma.challengetokonyadiaapi.service.ProductImageService;
import com.enigma.challengetokonyadiaapi.service.ProductService;
import com.enigma.challengetokonyadiaapi.service.StoreService;
import com.enigma.challengetokonyadiaapi.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ValidationUtil validationUtil;
    private final ProductImageService productImageService;
    private final StoreService storeService;


    @Override
    public Product getById(String id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
        );

//        return ProductResponse.builder()
//                .id(product.getId())
//                .stock(product.getStock())
//                .description(product.getDescription())
//                .price(product.getPrice())
//                .name(product.getName())
//                .build();
    }


    @Override
    public ProductResponse findById(String id) {
        Product product = getProductOrElseThrow(id);

        return ProductResponse.builder()
                .id(product.getId())
                .stock(product.getStock())
                .description(product.getDescription())
                .price(product.getPrice())
                .name(product.getName())
                .build();
    }

    @Override
    public ProductResponse udpate(ProductRequest request) {
        validationUtil.validate(request);
        Product product = getProductOrElseThrow(request.getId());
        product.setStock(request.getStock());
        product.setDescription(request.getDescription());
        product.setName(request.getName());
        repository.saveAndFlush(product);
        return ProductResponse.builder()
                .id(product.getId())
                .stock(product.getStock())
                .description(product.getDescription())
                .price(product.getPrice())
                .name(product.getName())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductResponse createNew(ProductRequest request) {
        validationUtil.validate(request);
        try {
            List<ProductImage> images = request.getMultipartFile()
                    .stream()
                    .map(productImageService::createImage
                    ).collect(Collectors.toList());

            Store store = storeService.getById(request.getStoreId());
            Product product = Product.builder()
                    .store(store)
                    .name(request.getName())
                    .stock(request.getStock())
                    .price(request.getPrice())
                    .description(request.getDescription())
                    .build();
            repository.saveAndFlush(product);
            images.forEach(image -> image.setProduct(product));

            List<FileResponse> productImages = new ArrayList<>(toFileResponse(images));

            return ProductResponse.builder()
                    .id(product.getId())
                    .stock(product.getStock())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .name(product.getName())
                    .fileResponses(productImages)
                    .build();
        } catch (Exception e) {
//            String fileName =String.format("%d_%s",System.currentTimeMillis(),request.getMultipartFile().getOriginalFilename());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ProductResponse> findAll() {
        List<Product> products = repository.findAll();
        List<ProductResponse> responses = products.stream()
                .map(product ->

                        ProductResponse.builder()
                                .price(product.getPrice())
                                .id(product.getId())
                                .stock(product.getStock())
                                .name(product.getName())
                                .description(product.getDescription())
                                .fileResponses(toFileResponse(product.getProductImages()))
                                .build()
                )
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public void delete(String id) {
        Product product = getProductOrElseThrow(id);
        repository.delete(product);
        product.getProductImages().forEach(productImage -> productImageService.deleteFile(productImage.getPath()));
    }
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
//    public void handleFailedProductSave(String pathFile){
//
//    }

    private Product getProductOrElseThrow(String id) {
        Product product = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
        );
        return product;
    }



    private List<FileResponse> toFileResponse(List<ProductImage> list){
        return list.stream().map(productImage -> FileResponse.builder()
                .fileName(productImage.getName())
                .url("/api/product/" + productImage.getId() + "/image")
                .build()).collect(Collectors.toList());
    }
}
