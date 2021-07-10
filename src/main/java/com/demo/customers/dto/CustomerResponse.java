package com.demo.customers.dto;

import lombok.*;

import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerResponse {
    private String country;
    private String state;
    private String countryCode;
    private String phone;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerResponse that = (CustomerResponse) o;
        return country.equals(that.country) && state.equals(that.state) && countryCode.equals(that.countryCode) && phone.equals(that.phone) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, state, countryCode, phone, name);
    }
}
