package com.enigma.challengetokonyadiaapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_transaction_detail")
@Builder(toBuilder = true)
public class TransactionDetail {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    @JsonBackReference
    private Transaction transaction;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
    @Column
    private Integer quantity;
    @Column
    private Long price;


}
