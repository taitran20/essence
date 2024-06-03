package com.tai.essence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Promotion name is required")
    private String name;

    @NotBlank(message = "Promotion description is required")
    private String description;

    private double discountPercentage;

    @Temporal(TemporalType.DATE)
    private Date startAt;

    @Temporal(TemporalType.DATE)
    private Date endAt;


    private boolean active;
}
