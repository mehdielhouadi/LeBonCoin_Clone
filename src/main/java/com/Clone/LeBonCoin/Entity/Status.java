package com.Clone.LeBonCoin.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String status;

    @OneToMany(mappedBy = "status")
    private Set<Announcement> announcements;
    public Status(String status){
        this.status = status;
        announcements = new HashSet<>();
    }
}
