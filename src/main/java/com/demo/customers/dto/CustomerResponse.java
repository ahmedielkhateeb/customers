package com.demo.customers.dto;

import lombok.*;

import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerResponse {
    private String country;
    private String state;
    private String countryCode;
    private String phone;
    private String name;
}
