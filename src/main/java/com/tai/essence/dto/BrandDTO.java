package com.tai.essence.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandDTO {
    private Long id;
    @NotEmpty(message = "Brand name can't be empty")
    private String name;

    private String description;

    private String imgUrl;
}
