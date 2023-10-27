package com.enigma.challengetokonyadiaapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "m_customer")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private  String  id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(nullable = false,name = "phone_number")
    private String phoneNumber;
    @Column(name = "address")
    private String address;


    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_credential_id")
    @JsonManagedReference
    private UserCredential userCredential;

}
