package com.tai.essence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "discount")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 125, nullable = false)
    private String code;

    @Column(nullable = false)
    private Double discountAmount;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;
}
