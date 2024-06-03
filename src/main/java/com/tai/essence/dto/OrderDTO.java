package com.tai.essence.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tai.essence.entity.Address;
import com.tai.essence.entity.OrderDetail;
import com.tai.essence.entity.Promotion;
import com.tai.essence.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO extends BaseDTO{
    private Long id;

    private UserDTO userDTO;

    private AddressDTO addressDTO;

    private String note;

    private LocalDateTime orderDate;

    private String status;

    private Float totalPrice;

    private double total_discountPrice;

    private String shippingMethod;

    private LocalDateTime shippingDate;

    private String trackingNumber;

    private String paymentMethod;

    private Boolean active;

    private List<OrderDetailDTO> orderDetailDTOS;

    private Promotion promotion;
}
