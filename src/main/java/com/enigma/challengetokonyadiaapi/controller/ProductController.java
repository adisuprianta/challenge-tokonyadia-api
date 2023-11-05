package com.enigma.challengetokonyadiaapi.controller;

import com.enigma.challengetokonyadiaapi.dto.request.ProductRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CommonResponse;
import com.enigma.challengetokonyadiaapi.dto.response.ProductResponse;
import com.enigma.challengetokonyadiaapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "api/store")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

//    @GetMapping("/{id}/image")
//    public ResponseEntity<?> downloadMenuImage(@PathVariable String id){
//        Resource resource = productService.getMenuImageById(id);
//        String headerValues = "attachment; filename=\""+resource.getFilename()+"\"";
//        return ResponseEntity.status(HttpStatus.OK)
//                .header(HttpHeaders.CONTENT_DISPOSITION,headerValues)
//                .body(resource);
//    }

    @PostMapping()
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> createMenu(
            @RequestParam String name,
            @RequestParam Long price,
            @RequestParam Integer stock,
            @RequestParam String description,
            @RequestParam String storeId,
            @RequestParam MultipartFile multipartFile

    ) {
        ProductRequest request = ProductRequest.builder()
                .stock(stock)
                .description(description)
                .storeId(storeId)
                .price(price)
                .multipartFile(multipartFile)
                .name(name)
                .build();
        ProductResponse productResponse = productService.createNew(request);
        CommonResponse<ProductResponse> response = CommonResponse.<ProductResponse>builder()
                .message("successfully create new menu")
                .statusCode(HttpStatus.CREATED.value())
                .data(productResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
