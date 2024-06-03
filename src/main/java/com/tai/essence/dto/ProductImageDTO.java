package com.tai.essence.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDTO {
    private Long id;
    private ProductDTO productDTO;
    private String imageUrl;
}
