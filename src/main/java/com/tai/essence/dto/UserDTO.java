package com.tai.essence.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tai.essence.entity.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;

    private String first_name;

    private String last_name;

    private String email;

    private String phoneNumber;

    private List<AddressDTO> addressDTOS;

    private String password;

    private boolean active;

    private String phone;

    private Date dateOfBirth;

    private int facebookAccountId;

    private int googleAccountId;

    private RoleDTO roleDTO;

    private List<OrderDTO> orderDTOS;

    private List<Post> posts;

    private List<ReviewDTO> reviewDTOS;
}
