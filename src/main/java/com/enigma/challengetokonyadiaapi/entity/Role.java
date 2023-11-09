package com.enigma.challengetokonyadiaapi.entity;

import com.enigma.challengetokonyadiaapi.constant.ERole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "m_role")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Role {
    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
    @Enumerated(EnumType.STRING)
    private ERole name;

    @ManyToMany(mappedBy = "likeRole")
    @JsonBackReference
    private Set<UserCredential> roles;
}
