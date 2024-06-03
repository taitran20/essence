package com.tai.essence.dto;

import com.tai.essence.entity.Product;
import com.tai.essence.entity.User;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {
    private User user;

    private Product product;
    private int rating;
    private String comment;
    private LocalDateTime createAt;
}
