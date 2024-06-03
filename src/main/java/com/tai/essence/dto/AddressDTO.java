package com.tai.essence.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tai.essence.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class AddressDTO {
    private Integer id;
    private String fName;
    private String lName;
    private String streetAddress;
    private String commune;
    private String district;
    private String province;
    private String zipCode;
    private User user;
    private String mobile;
}
