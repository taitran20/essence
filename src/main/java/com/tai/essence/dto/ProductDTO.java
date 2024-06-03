package com.tai.essence.dto;

import com.tai.essence.entity.ProductImage;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO extends BaseDTO{
    private Long id;

    @NotBlank(message = "Title not required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String name;

    @Min(value = 0, message = "Price must be greater than 0")
    @Positive
    @NotNull
    private Float price;

    @Min(value = 0, message = "Quantity must be greater than 0")
    @NotNull
    private Integer quantity;

    private String thumbnail;

    private String description;

    @NotNull
    private CategoryDTO categoryDTO;

    @NotNull
    private BrandDTO brandDTO;

    @NotEmpty
    private Set<com.tai.essence.entity.Size> sizeSet;

    private List<ProductImage> productImages;

    @PositiveOrZero
    @Min(value = 0, message = "Discount must be greater than 0")
    private double discountPrice;

    @PositiveOrZero
    @Max(value = 100, message = "Discount must be less than or equal to 100")
    private double discountPercent;

    private String color;
}
