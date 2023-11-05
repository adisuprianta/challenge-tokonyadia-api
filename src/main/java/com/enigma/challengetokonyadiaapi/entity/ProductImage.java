package com.enigma.challengetokonyadiaapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_product_image")
@Builder
public class ProductImage {
    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
    private String name;
    private String contentType;
    private String path;
    private Long size;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

}
