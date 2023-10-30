package com.enigma.challengetokonyadiaapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_transaction")
@Builder(toBuilder = true)
@Data
public class Transaction {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "transaction_date")
    private Date transDate;

    @OneToMany(mappedBy = "transaction",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TransactionDetail> transactionDetails ;

}
