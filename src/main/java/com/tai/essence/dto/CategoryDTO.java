package com.tai.essence.dto;

import com.tai.essence.entity.Category;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
    private Long id;
    @NotEmpty(message = "Category name can't be empty")
    private String name;
    private CategoryDTO parentCategoryDTO;
    private int level;
}
