package com.tai.essence.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO {
    private Long id;
    private String name;
    public static String ADMIN = "ADMIN";
    public static String USER = "USER";
    public static String SHIPPER = "SHIPPER";
}
