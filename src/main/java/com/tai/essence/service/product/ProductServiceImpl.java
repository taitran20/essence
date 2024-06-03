package com.tai.essence.service.product;

import com.tai.essence.dto.ProductDTO;
import com.tai.essence.dto.ProductImageDTO;
import com.tai.essence.entity.Brand;
import com.tai.essence.entity.Product;
import com.tai.essence.entity.ProductImage;
import com.tai.essence.exception.DuplicateNameException;
import com.tai.essence.exception.EntityNotFoundException;
import com.tai.essence.exception.InvalidParamException;
import com.tai.essence.helper.mapping.ProductMapping;
import com.tai.essence.repository.ProductImageRepository;
import com.tai.essence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tai.essence.entity.ProductImage.MAXIMUM_IMAGES_PER_PRODUCT;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapping productMapping;
    private final ProductImageRepository productImageRepository;
    @Override
    public List<ProductDTO> findAll() {
        log.info("Product service: fetch all product");
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapping::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(Long productId) {
        log.info("Product service: find product by id: " + productId);
        Product product = productRepository.findById(productId).orElseThrow(null);
        return productMapping.entityToDto(product);
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) throws EntityNotFoundException, DuplicateNameException {
        log.info("Product service insert product name :" + productDTO.getName());
        Optional<Product> productOptional = productRepository.findByName(productDTO.getName());
        if(productOptional.isEmpty()){
            try {
                Product product = productMapping.dtoToEntity(productDTO);
                return productMapping.entityToDto(productRepository.save(product));
            } catch (DataAccessException e) {
                log.error("Error saving product: Data integrity violation", e);
                throw new EntityNotFoundException("Error saving product: Data integrity violation");
            } catch (Exception e) {
                log.error("Error saving product", e);
                throw new EntityNotFoundException(e.getMessage());
            }
        }else {
            throw new DuplicateNameException("Name product must be unique");
        }

    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        return null;
    }

    @Override
    public ProductDTO update(Long productId, ProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isPresent()){
            try {
                Product product = productMapping.dtoToEntity(productDTO);
                product.setId(productId);
                productRepository.save(product);
                return productDTO;
            }catch (Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        }else {
            return null;
        }
    }

    @Override
    public void deleteById(Long productId) {
        log.info("Product service: delete product by id: " + productId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            productRepository.deleteById(productId);
        }
        else {
            log.error("Product not found with ID: {}", productId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found with ID: " + productId);
        }
    }

    @Override
    public Page<ProductDTO> getAllProduct(Long categoryId, List<String> colors, List<String> sizes, Double minPrice, Double maxPrice, Double minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Product> products = productRepository.filterProducts(categoryId, minPrice, maxPrice, minDiscount, sort);
        if(!colors.isEmpty()){
            products = products.stream().filter(p -> colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }
        if (stock != null){
            if (stock.equals("in_stock")){
                products = products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
            }else if (stock.equals("out_of_stock")){
                products = products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
            }

        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
        List<Product> pageContent = products.subList(startIndex, endIndex);
        List<ProductDTO> pageContentDTO = new ArrayList<>();
        for (Product p:
                pageContent) {
            pageContentDTO.add(productMapping.entityToDto(p));
        }
        return new PageImpl<>(pageContentDTO,pageable, products.size());
    }

    @Override
    public ProductImage createProductImage(Long id, ProductImageDTO productImageDTO) throws InvalidParamException {
        ModelMapper modelMapper = new ModelMapper();
        ProductDTO existsProduct = findById(id);
        ProductImageDTO new_productImage = ProductImageDTO.builder()
                .productDTO(existsProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        int total_image = productImageRepository.findByProductId(id).size();
        if(total_image > MAXIMUM_IMAGES_PER_PRODUCT){
            throw new InvalidParamException("Number of image must <= 5");
        }
        return productImageRepository.save(modelMapper.map(new_productImage, ProductImage.class));
    }

    @Override
    public boolean existsByName(String productName) {
        Optional<Product> product = productRepository.findByName(productName);
        return product.isPresent();
    }
}
