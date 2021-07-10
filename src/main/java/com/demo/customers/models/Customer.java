package com.demo.customers.models;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer", indexes = @Index(name = "customer_phone_idx", columnList = "phone", unique = true))
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    @Setter(value = AccessLevel.NONE)
    private Integer id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "phone")
    @NotNull
    private String phone;

    @Transient
    @Setter(value = AccessLevel.NONE)
    @Getter(value = AccessLevel.NONE)
    private String countryCode;

    @Transient
    @Setter(value = AccessLevel.NONE)
    @Getter(value = AccessLevel.NONE)
    private String country;

    @Transient
    @Setter(value = AccessLevel.NONE)
    @Getter(value = AccessLevel.NONE)
    private String state;

    @Transient
    public static final Map<String, String> countries = new HashMap<String, String>() {
        {
            put("237", "Cameroon");
            put("251", "Ethiopia");
            put("212", "Morocco");
            put("258", "Mozambique");
            put("256", "Uganda");

        }
    };


    public String getCountryCode() {
        return getPhone().substring(1, 4);
    }

    public String getCountry() {
        return countries.get(getCountryCode());
    }

    public String getState() {
        final Map<String, String> countriesRegex = new HashMap<String, String>() {
            {
                put("237", "\\(237\\)\\ ?[2368]\\d{7,8}$");
                put("251", "\\(251\\)\\ ?[1-59]\\d{8}$");
                put("212", "\\(212\\)\\ ?[5-9]\\d{8}$");
                put("258", "\\(258\\)\\ ?[28]\\d{7,8}$");
                put("256", "\\(256\\)\\ ?\\d{9}$");

            }
        };
        return getPhone().matches(countriesRegex.get(getCountryCode())) ? "Valid" : "Invalid";
    }
}
