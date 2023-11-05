package com.enigma.challengetokonyadiaapi.dto.request;

import com.enigma.challengetokonyadiaapi.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductRequest {
    private String id;
    private String name;
    private String description;
    private Long price;
    private Integer stock;
    private String storeId;
    private MultipartFile multipartFile;
}
