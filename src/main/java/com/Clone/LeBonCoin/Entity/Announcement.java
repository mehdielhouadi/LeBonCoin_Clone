package com.Clone.LeBonCoin.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Announcement {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private float price;
    private String type;
    private String description;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="status_id")
    private Status status;
    @ManyToOne
    @JoinColumn(name="visitor_id", nullable=false)
    private Visitor visitor;
}
