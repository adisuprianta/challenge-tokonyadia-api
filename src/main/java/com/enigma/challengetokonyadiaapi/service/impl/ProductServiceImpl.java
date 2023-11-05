package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.dto.request.ProductRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ValidationUtil validationUtil;
    private final ProductImageService productImageService;
    private final StoreService storeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Product getById(String id) {
        return repository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found")
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
        ProductImage image = productImageService.createImage(request.getMultipartFile());
        StoreResponse storeResponse = storeService.findById(request.getStoreId());
        Store store = Store.builder()
                .id(storeResponse.getId())
                .build();
        Product product = Product.builder()
                .store(store)
                .name(request.getName())
                .stock(request.getStock())
                .price(request.getPrice())
                .description(request.getDescription())
                .build();
        repository.saveAndFlush(product);
        image.setProduct(product);
        return null;
    }

    private Product getProductOrElseThrow(String id) {
        Product product = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
        );
        return product;
    }
}
