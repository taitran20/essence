package com.tai.essence.service.productImage;

import com.tai.essence.dto.ProductImageDTO;
import com.tai.essence.entity.ProductImage;

import java.util.List;

public interface ProductImageService {
    List<ProductImageDTO> findByProductId(Long id);
}
