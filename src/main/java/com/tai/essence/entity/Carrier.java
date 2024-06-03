package com.tai.essence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "carriers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Carrier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contactInfo;
    private String website;

    @OneToMany(mappedBy = "carrier", cascade = CascadeType.ALL)
    private List<ShippingInfo> shippingInfos;
}
