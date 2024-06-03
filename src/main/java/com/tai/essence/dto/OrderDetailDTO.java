package com.tai.essence.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {
    private Long id;

    private OrderDTO orderDTO;

    private ProductDTO productDTO;

    private double price;

    private double discountPrice;

    private int numberOfProducts;

    private double totalPrice;

    private String color;

    private String size;
}
