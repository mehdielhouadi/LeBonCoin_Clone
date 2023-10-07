package com.Clone.LeBonCoin.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Visitor {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String pseudo;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private Date subscriptionDate = new Date();

    @ManyToMany(targetEntity = Role.class,cascade = CascadeType.ALL)
    @JoinTable(name = "visitor_role",
            joinColumns = @JoinColumn(name = "visitor_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set<Role> roles;
    @OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL)
    private Set<Announcement> Announcements;
}


