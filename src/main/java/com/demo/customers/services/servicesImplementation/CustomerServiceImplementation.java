package com.demo.customers.services.servicesImplementation;

import com.demo.customers.dto.CountryResponse;
import com.demo.customers.dto.CustomerResponse;
import com.demo.customers.exceptions.custom.NotAcceptableException;
import com.demo.customers.exceptions.custom.NotFoundException;
import com.demo.customers.models.Customer;
import com.demo.customers.repositories.CustomerRepository;
import com.demo.customers.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Primary
public class CustomerServiceImplementation implements CustomerService {

    private final CustomerRepository customerRepository;
    private final Logger log = LoggerFactory.getLogger(CustomerServiceImplementation.class);

    @Value("#{${countries.map}}")
    private Map<String, String> countries;


    @Autowired
    public CustomerServiceImplementation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public List<CustomerResponse> getCustomersByCountryCodeAndState(String countryCode, String state)
            throws NotAcceptableException, NotFoundException {

        log.info("Enter getCustomersByCountryCodeAndState Method of CustomerService Class, with parameters countryCode:" + countryCode + " and state:" + state);

        List<CustomerResponse> customerResponseList;

        if (countryCode.equalsIgnoreCase("any") && state.equalsIgnoreCase("any")) {
            customerResponseList = getAllCustomers();
        } else if (countryCode.equalsIgnoreCase("any") && !state.equalsIgnoreCase("any")) {
            customerResponseList = getAllCustomersWithAnyCountryCodeAndSpecificState(state);
        } else if (!countryCode.equalsIgnoreCase("any") && state.equalsIgnoreCase("any")) {
            customerResponseList = getAllCustomersWithSpecificCountryCodeAndAnyState(countryCode);
        } else {
            customerResponseList = getAllCustomersWithSpecificCountryCodeAndSpecificState(countryCode, state);
        }
        log.info("Exit getCustomersByCountryCodeAndState Method of CustomerService Class");
        return customerResponseList;
    }


    @Override
    public List<CountryResponse> getCountries() {
        log.info("Enter getCountries Method of CustomerService Class");

        List<CountryResponse> countryResponseList = new ArrayList<>();
        countries.forEach((countryCode, countryName) -> {
            CountryResponse countryResponse = new CountryResponse(countryName, countryCode);
            countryResponseList.add(countryResponse);
        });

        log.info("Exit getCountries Method of CustomerService Class");
        return countryResponseList;
    }


    private List<CustomerResponse> getAllCustomers() throws NotFoundException {
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        List<Customer> customers = customerRepository.findAll();
        return getCustomerResponseList(customerResponseList, customers);
    }


    private List<CustomerResponse> getAllCustomersWithAnyCountryCodeAndSpecificState(String state)
            throws NotFoundException, NotAcceptableException {

        List<CustomerResponse> customerResponseList = new ArrayList<>();

        if (state.equalsIgnoreCase("valid") || state.equalsIgnoreCase("invalid")) {

            List<Customer> customers = customerRepository.findAll();
            if (customers.isEmpty()) {
                log.error("No customers found");
                throw new NotFoundException("No customers found");
            }
            filterTheValidAndTheInvalidCustomerState(state, customerResponseList, customers);
        } else {
            log.error("state can't be " + state + " it must be valid or invalid");
            throw new NotAcceptableException("state can't be " + state + " it must be valid or invalid");
        }
        return customerResponseList;
    }


    private List<CustomerResponse> getAllCustomersWithSpecificCountryCodeAndAnyState(String countryCode)
            throws NotFoundException {

        List<CustomerResponse> customerResponseList = new ArrayList<>();

        if (!countries.containsKey(countryCode)) {
            log.error("country code (" + countryCode + ") not found");
            throw new NotFoundException("country code (" + countryCode + ") not found");
        }
        List<Customer> customers = customerRepository.findByCountry("(" + countryCode + ")%");
        return getCustomerResponseList(customerResponseList, customers);
    }


    private List<CustomerResponse> getCustomerResponseList(List<CustomerResponse> customerResponseList
            , List<Customer> customers) throws NotFoundException {
        if (customers.isEmpty()) {
            log.error("No customers found");
            throw new NotFoundException("No customers found");
        }
        customers.forEach(customer -> {
            CustomerResponse customerResponse = new CustomerResponse(customer.getCountry(), customer.getState(),
                    "+" + customer.getCountryCode(), customer.getPhone().split(" ")[1],
                    customer.getName());
            customerResponseList.add(customerResponse);
        });
        return customerResponseList;
    }


    private List<CustomerResponse> getAllCustomersWithSpecificCountryCodeAndSpecificState(String countryCode
            , String state) throws NotFoundException, NotAcceptableException {
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        if (!countries.containsKey(countryCode)) {
            log.error("country code (" + countryCode + ") not found");
            throw new NotFoundException("country code (" + countryCode + ") not found");
        }
        //check if undefined state entered by the user
        if (state.equalsIgnoreCase("valid") || state.equalsIgnoreCase("invalid")) {
            List<Customer> customers = customerRepository.findByCountry("(" + countryCode + ")%");
            if (customers.isEmpty()) throw new NotFoundException("No customers found");
            filterTheValidAndTheInvalidCustomerState(state, customerResponseList, customers);
        } else {
            log.error("state can't be " + state + " it must be valid or invalid");
            throw new NotAcceptableException("state can't be " + state + " it must be valid or invalid");
        }
        return customerResponseList;
    }


    private void filterTheValidAndTheInvalidCustomerState(String state, List<CustomerResponse> customerResponseList
            , List<Customer> customers) {
        if (state.equalsIgnoreCase("valid")) {
            customers.stream().filter(customer -> customer.getState().equalsIgnoreCase("valid")).
                    forEach(customer -> {
                        CustomerResponse customerResponse = new CustomerResponse(customer.getCountry(),
                                customer.getState(), "+" + customer.getCountryCode(),
                                customer.getPhone().split(" ")[1], customer.getName());
                        customerResponseList.add(customerResponse);
                    });
        } else {
            customers.stream().filter(customer -> customer.getState().equalsIgnoreCase("invalid")).
                    forEach(customer -> {
                        CustomerResponse customerResponse = new CustomerResponse(customer.getCountry(),
                                customer.getState(), "+" + customer.getCountryCode(),
                                customer.getPhone().split(" ")[1], customer.getName());
                        customerResponseList.add(customerResponse);
                    });
        }
    }
}
