package com.tai.essence.service.brand;

import com.tai.essence.entity.Brand;
import com.tai.essence.exception.DuplicateNameException;
import com.tai.essence.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    @Override
    public List<Brand> findAll() {
        log.info("Brand list, service, fetch all brands");
        return brandRepository.findAll();
    }

    @Override
    public Brand findById(Long id) {
        log.info("Brand, service, fetch brand by id");
        return brandRepository.findById(id).orElseThrow(null);
    }

    @Override
    @Transactional
    public boolean insert(Brand dto) throws DuplicateNameException {
        log.info("Brand, service, insert brand");
        Optional<Brand> optionalBrand = brandRepository.findByName(dto.getName());
        if (optionalBrand.isEmpty()){
            try {
                Brand brand = new Brand();
                brand.setName(dto.getName());
                brand.setDescription(dto.getDescription());
                brand.setImgUrl(dto.getImgUrl());
                brandRepository.save(brand);
                return true;
            }catch (Exception e) {
                log.error("Brand, service, error insert brand");
                throw new RuntimeException(e);
            }
        }
        else {
            log.error("Brand, service, error duplicate name");
            throw new DuplicateNameException("Name is already exist");
        }

    }

    @Override
    @Transactional
    public boolean update(Long id, Brand dto) {
        log.info("Starting update brand ");
        try {
            Brand brand = brandRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Brand not found."));
            brand.setName(dto.getName());
            brand.setDescription(dto.getDescription());
            brand.setImgUrl(dto.getImgUrl());

            brandRepository.save(brand);
            log.info("Brand update successfully");
            return true;
        }catch (Exception e){
            log.error("Error update");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating brand.");
        }
    }

    @Override
    public boolean delete(Long id) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        try {
            if(optionalBrand.isPresent()){
                log.info("Deleting brand with ID: {}", id);
                brandRepository.deleteById(id);
                return true;
            }
            else {
                log.error("Brand not found with ID: {}", id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found with ID: " + id);
            }
        } catch (NullPointerException ex) {
            log.error("Null value returned from findById() for ID: {}", id);
            throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
