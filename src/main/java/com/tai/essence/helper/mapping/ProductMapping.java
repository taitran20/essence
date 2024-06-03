package com.tai.essence.helper.mapping;

import com.tai.essence.dto.BrandDTO;
import com.tai.essence.dto.CategoryDTO;
import com.tai.essence.dto.ProductDTO;
import com.tai.essence.entity.Brand;
import com.tai.essence.entity.Category;
import com.tai.essence.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductMapping implements BaseMapping<Product, ProductDTO> {
    @Override
    public ProductDTO entityToDto(final Product entity) {
        ModelMapper modelMapper = new ModelMapper();
        BrandDTO brandDTO = modelMapper.map(entity.getBrand(), BrandDTO.class);
        ProductDTO productDTO =  ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .categoryDTO(CategoryDTO.builder()
                        .id(entity.getCategory().getId())
                        .name(entity.getCategory().getName())
                        .level(entity.getCategory().getLevel())
                        .build())
                .brandDTO(brandDTO)
                .price(entity.getPrice())
                .description(entity.getDescription())
                .sizeSet(entity.getSizeSet())
                .productImages(entity.getProductImages())
                .color(entity.getColor())
                .discountPrice(entity.getDiscountPrice())
                .discountPercent(entity.getDiscountPercent())
                .thumbnail(entity.getThumbnail())
                .quantity(entity.getQuantity())
                .build();
        productDTO.setCreatedAt(entity.getCreatedAt());
        productDTO.setUpdatedAt(entity.getUpdatedAt());
        return productDTO;
    }

    @Override
    public Product dtoToEntity(final ProductDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Brand brand = modelMapper.map(dto.getBrandDTO(), Brand.class);
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .category(Category.builder()
                        .id(dto.getCategoryDTO().getId())
                        .level(dto.getCategoryDTO().getLevel())
                        .name(dto.getCategoryDTO().getName())
                        .build())
                .reviews(null)
                .brand(brand)
                .price(dto.getPrice())
                .description(dto.getDescription())
                .sizeSet(dto.getSizeSet())
                .color(dto.getColor())
                .discountPrice(dto.getDiscountPrice())
                .discountPercent(dto.getDiscountPercent())
                .thumbnail(dto.getThumbnail())
                .quantity(dto.getQuantity())
                .build();
    }
}
