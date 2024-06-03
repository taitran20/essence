package com.tai.essence.api.controller.product;

import com.github.javafaker.Faker;
import com.tai.essence.dto.BrandDTO;
import com.tai.essence.dto.CategoryDTO;
import com.tai.essence.dto.ProductDTO;
import com.tai.essence.dto.ProductImageDTO;
import com.tai.essence.entity.ProductImage;
import com.tai.essence.entity.Size;
import com.tai.essence.exception.DuplicateNameException;
import com.tai.essence.exception.EntityNotFoundException;
import com.tai.essence.service.product.ProductService;
import com.tai.essence.service.productImage.ProductImageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static com.tai.essence.entity.ProductImage.MAXIMUM_IMAGES_PER_PRODUCT;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final ProductImageService productImageService;

    @GetMapping("")
    public ResponseEntity<Object> findAll() {
        log.info("Product controller: fetch all products");
        try {
            List<ProductDTO> list = productService.findAll();
            if(list.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(list);
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @PostMapping("")
    public ResponseEntity<Object> save(@Valid @RequestBody ProductDTO productDTO,
                                       BindingResult bindingResult) {
        log.info("Product controller insert product name :" + productDTO.getName());
        if(bindingResult.hasErrors()){
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        try {
            ProductDTO savedProduct = productService.save(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        }catch (DuplicateNameException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id , @Valid @RequestBody ProductDTO productDTO,
                                         BindingResult bindingResult){
        log.info("Product controller: update product name: " + productDTO.getName());
        if(bindingResult.hasErrors()){
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        try {
            productService.update(id, productDTO);
            return ResponseEntity.ok().body("success");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImages(@PathVariable("id") Long productId,
                                               @RequestParam("files") List<MultipartFile> files){
        try {
            ProductDTO existProduct = productService.findById(productId);
            List<ProductImage> productImages = new ArrayList<>();
            files = files == null ? new ArrayList<>() : files;
            if(files.size() > MAXIMUM_IMAGES_PER_PRODUCT){
                return ResponseEntity.badRequest().body("Maximum 5 file upload");
            }
            if((files.size() + productImageService.findByProductId(productId).size()) > MAXIMUM_IMAGES_PER_PRODUCT){
                return ResponseEntity.badRequest().body("Maximum 5 file upload for product");
            }

            for (MultipartFile file: files) {
                if (file.getSize() == 0){
                    continue;
                }
                if(file.getSize() > 10*1024*1024){
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File too large, maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be image");
                }
                String fileName = storeFile(file);
                ProductImage productImage = productService.createProductImage(existProduct.getId(),
                        ProductImageDTO.builder()
                                .imageUrl(fileName)
                                .build());
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    private String storeFile(MultipartFile file) throws IOException {
        if(!isImageFile(file) || file.getOriginalFilename() == null){
            throw new IOException("Invalid image format");
        }
        String nameFile = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFilename = UUID.randomUUID().toString() + "_" + nameFile;
        Path uploadDir = Paths.get("uploads");
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    private boolean isImageFile(MultipartFile file){
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> findById(@PathVariable Long productId){
        log.info("Product controller find product by id :" + productId);
        return ResponseEntity.ok().body(productService.findById(productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> delete(@PathVariable Long productId){
        log.info("Product controller delete product by id :" + productId);
        try {
            productService.deleteById(productId);
            return ResponseEntity.ok().body("Delete successfully");
        }catch (ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<Object> filterProduct(@RequestParam(required = false) Long categoryId,
                                                @RequestParam(required = false) List<String> color,
                                                @RequestParam(required = false) List<String> size,
                                                @RequestParam(required = false) Double minPrice,
                                                @RequestParam(required = false) Double maxPrice,
                                                @RequestParam(required = false) Double minDiscount,
                                                @RequestParam(required = false) String sort,
                                                @RequestParam(required = false) String stock,
                                                @RequestParam(defaultValue = "1") Integer pageNumber,
                                                @RequestParam(defaultValue = "10") Integer pageSize){
        try {
            Page<ProductDTO> productDTOS = productService.getAllProduct(categoryId,color,size,minPrice,maxPrice,minDiscount,sort,stock,pageNumber,pageSize);
            return new ResponseEntity<>(productDTOS, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/generateFakeProduct")
    public ResponseEntity<Object> generateFakeProduct() throws DuplicateNameException, EntityNotFoundException {
        try {
            Faker faker = new Faker();
            Set<Size> sizeSet = new HashSet<>();
            Size size01 = new Size("36",50);
            Size size02 = new Size("37",50);
            sizeSet.add(size01);
            sizeSet.add(size02);
            for (int i = 0; i < 10000; i++) {
                String productName = faker.commerce().productName();
                if (productService.existsByName(productName)) {
                    continue;
                }
                ProductDTO productDTO = ProductDTO.builder()
                        .name(productName)
                        .price((float) faker.number().numberBetween(100, 10000))
                        .description(faker.lorem().sentence())
                        .thumbnail("")
                        .categoryDTO(CategoryDTO.builder()
                                .id((long) faker.number().numberBetween(2, 6))
                                .build())
                        .brandDTO(BrandDTO.builder()
                                .id((long) faker.number().numberBetween(1, 2))
                                .build())
                        .sizeSet(sizeSet)
                        .quantity(100)
                        .discountPrice((float) faker.number().numberBetween(50, 90))
                        .discountPercent((float) faker.number().numberBetween(1, 90))
                        .color(faker.color().name())
                        .build();
                productService.save(productDTO);
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Fake success");
    }
}
