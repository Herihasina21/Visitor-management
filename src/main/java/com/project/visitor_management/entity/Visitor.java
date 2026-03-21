package com.project.visitor_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "visitors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitorId;

    private String name;

    private int numberOfDays;

    private double dailyRate;

    @Transient
    public double getTotalFee() {
        return this.numberOfDays * this.dailyRate;
    }
}