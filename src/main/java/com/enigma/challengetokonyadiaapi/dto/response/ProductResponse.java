package com.enigma.challengetokonyadiaapi.dto.response;

import com.enigma.challengetokonyadiaapi.entity.Store;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductResponse {

    private String id;
    private String name;
    private String description;
    private Long price;
    private Integer stock;
    private List<FileResponse> fileResponses;
}
