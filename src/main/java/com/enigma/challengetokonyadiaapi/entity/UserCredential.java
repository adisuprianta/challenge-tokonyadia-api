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
@Table(name = "m_user_credential")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCredential {
    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
    @Column
    private String email;
    @Column
    private String password;

    @OneToOne(mappedBy = "userCredential",cascade = CascadeType.ALL)
    @JsonBackReference
    private Customer customer;

}
