package com.enigma.challengetokonyadiaapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "m_user_credential")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserCredential {
    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
    @Column
    private String username;
    @Column
    private String password;

    @OneToOne(mappedBy = "userCredential",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Customer customer;

//    @ManyToOne
//    private Role role;
    @ManyToMany
    @JoinTable(
            name = "role_like",
            joinColumns = @JoinColumn(name = "user_credential_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonManagedReference
    private Set<Role> likeRole;

}
