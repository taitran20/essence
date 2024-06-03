package com.tai.essence.service.productImage;

import com.tai.essence.dto.ProductImageDTO;
import com.tai.essence.entity.ProductImage;
import com.tai.essence.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService{
    private final ProductImageRepository productImageRepository;

    @Override
    public List<ProductImageDTO> findByProductId(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        List<ProductImage> productImageList =  productImageRepository.findByProductId(id);
        List<ProductImageDTO> productImageDTOS = new ArrayList<>();
        for (ProductImage productImage : productImageList) {
            ProductImageDTO productImageDTO = modelMapper.map(productImage, ProductImageDTO.class);
            productImageDTOS.add(productImageDTO);
        }
        return productImageDTOS;
    }
}
