package com.demo.customers.controllers;

import com.demo.customers.dto.CountryResponse;
import com.demo.customers.dto.CustomerResponse;
import com.demo.customers.exceptions.custom.NotAcceptableException;
import com.demo.customers.exceptions.custom.NotFoundException;
import com.demo.customers.models.Customer;
import com.demo.customers.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = {"/countryCode/{countryCode}/state/{state}", "/countryCode/{countryCode}/state/{state}/"})
    public ResponseEntity<?> getCustomers(@PathVariable String countryCode, @PathVariable String state) throws NotAcceptableException, NotFoundException {
        log.info("Enter getCustomers Method of CustomerController Class, with parameters countryCode:" + countryCode + " and state:" + state);
        List<CustomerResponse> customerResponseList = customerService.getCustomersByCountryCodeAndState(countryCode, state);
        log.info("Exit getCustomers Method of CustomerController Class");
        return ResponseEntity.ok(customerResponseList);
    }

    @GetMapping(value = {"/countries", "/countries/"})
    public ResponseEntity<?> getCountries() {
        log.info("Enter getCountries Method of CustomerController Class");
        List<CountryResponse> countryResponseList = customerService.getCountries();
        log.info("Exit getCustomers Method of CustomerController Class");
        return ResponseEntity.ok(countryResponseList);
    }
}
