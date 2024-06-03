package com.tai.essence.api.controller.brand;

import com.tai.essence.entity.Brand;
import com.tai.essence.exception.DuplicateNameException;
import com.tai.essence.service.brand.BrandService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/brand")
@Validated
public class BrandController {

    private final BrandService brandService;

    @GetMapping("")
    public ResponseEntity<List<Brand>> getAllBrands(){
        try {
            List<Brand> brands = brandService.findAll();
            if (brands.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(brands);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> createNewBrand(@Valid @RequestBody Brand brand,
                                                 BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        try {
            brandService.insert(brand);
            return ResponseEntity.status(HttpStatus.CREATED).body("Brand added successfully.");
        }catch (DuplicateNameException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An undefined error occurred");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @RequestBody Brand brand,
                                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        try{
            brandService.update(id, brand);
            return ResponseEntity.ok().body("Brand updated successfully.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBrand(@PathVariable Long id) {
        try {
            boolean deleted = brandService.delete(id);
            if (deleted) {
                return ResponseEntity.ok().body("Brand with ID " + id + " deleted successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException ex) {
            log.error("Invalid brand ID format: {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid brand ID format. Please provide a valid numeric ID.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An undefined error occurred");
        }
    }
}
