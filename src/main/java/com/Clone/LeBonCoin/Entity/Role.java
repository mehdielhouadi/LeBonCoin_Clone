package com.Clone.LeBonCoin.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)   // unecessary but usefull to get liste of admins or regular users
    private Set<Visitor> visitors;

    public Role(RoleEnum role){
        this.role = role;
        visitors = new HashSet<>();
    }
}
