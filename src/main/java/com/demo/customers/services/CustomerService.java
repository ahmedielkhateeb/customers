package com.demo.customers.services;

import com.demo.customers.dto.CountryResponse;
import com.demo.customers.dto.CustomerResponse;
import com.demo.customers.exceptions.custom.NotAcceptableException;
import com.demo.customers.exceptions.custom.NotFoundException;

import java.util.List;

public interface CustomerService {

    List<CustomerResponse> getCustomersByCountryCodeAndState(String countryCode, String state) throws NotAcceptableException, NotFoundException;

    List<CountryResponse> getCountries();
}
