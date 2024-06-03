package com.tai.essence.service.product;

import com.tai.essence.dto.ProductDTO;
import com.tai.essence.dto.ProductImageDTO;
import com.tai.essence.entity.ProductImage;
import com.tai.essence.exception.DuplicateNameException;
import com.tai.essence.exception.EntityNotFoundException;
import com.tai.essence.exception.InvalidParamException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAll();

    ProductDTO findById(Long productId);

    ProductDTO save(ProductDTO productDTO) throws EntityNotFoundException, DuplicateNameException;

    ProductDTO update(ProductDTO productDTO);

    ProductDTO update(Long productId,  ProductDTO productDTO);

    void deleteById(Long productId);

    Page<ProductDTO> getAllProduct(Long category, List<String> colors, List<String> sizes, Double minPrice, Double maxPrice,
                                   Double minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);

    ProductImage createProductImage(Long id, ProductImageDTO productImageDTO) throws InvalidParamException;

    boolean existsByName(String productName);
}
