package com.enigma.challengetokonyadiaapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_store")
@Builder(toBuilder = true)
public class Store {
    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "siup_number")
    private String siupNumber;
    @Column
    private String name;
    @Column
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "store",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Product> products;
}
