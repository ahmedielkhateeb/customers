package com.demo.customers.services;

import com.demo.customers.dto.CustomerResponse;
import com.demo.customers.exceptions.custom.NotAcceptableException;
import com.demo.customers.exceptions.custom.NotFoundException;
import com.demo.customers.logger.CustomerLogger;
import com.demo.customers.models.Customer;
import com.demo.customers.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final Map<String, String> countries = new HashMap<String, String>() {
        {
            put("237", "Cameroon");
            put("251", "Ethiopia");
            put("212", "Morocco");
            put("258", "Mozambique");
            put("256", "Uganda");

        }
    };

    private final Map<String, String> countriesRegex = new HashMap<String, String>() {
        {
            put("237", "\\(237\\)\\ ?[2368]\\d{7,8}$");
            put("251", "\\(251\\)\\ ?[1-59]\\d{8}$");
            put("212", "\\(212\\)\\ ?[5-9]\\d{8}$");
            put("258", "\\(258\\)\\ ?[28]\\d{7,8}$");
            put("256", "\\(256\\)\\ ?\\d{9}$");

        }
    };

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerResponse> getCustomersByCountryCodeAndState(String countryCode, String state) throws NotAcceptableException, NotFoundException {

        CustomerLogger.log.info("Enter getCustomersByCountryCodeAndState Method of CustomerService Class, with parameters countryCode:" + countryCode + " and state:" + state);

        List<CustomerResponse> customerResponseList = new ArrayList<>();

        // check if the filter is to find any country with any phone number state (find all the customers)
        if (countryCode.equalsIgnoreCase("any") && state.equalsIgnoreCase("any")) {

            List<Customer> customers = customerRepository.findAll();
            if (customers.isEmpty()) {
                //throw an exception if no customers found
                CustomerLogger.log.error("No customers found");
                throw new NotFoundException("No customers found");
            }
            customers.forEach(customer -> {
                String countryCodeOfCurrentCustomer = customer.getPhone().substring(1, 4);
                // validate to all customers state
                validateCustomerByCode(countryCodeOfCurrentCustomer, customerResponseList, customer);
            });

        }
        //check if the filter is to find any country with specific phone number state (find the customers of valid or invalid phone numbers)
        else if (countryCode.equalsIgnoreCase("any") && !state.equalsIgnoreCase("any")) {
            //check if undefined state entered by the user
            if (state.equalsIgnoreCase("valid") || state.equalsIgnoreCase("invalid")) {

                List<Customer> customers = customerRepository.findAll();
                if (customers.isEmpty()) {
                    //throw an exception if no customers found
                    CustomerLogger.log.error("No customers found");
                    throw new NotFoundException("No customers found");
                }
                // validate to all customers state
                validateCustomerByState(state, customerResponseList, customers);
            } else {

                //throw an exception if the state is undefined
                CustomerLogger.log.error("state can't be " + state + " it must be valid or invalid");
                throw new NotAcceptableException("state can't be " + state + " it must be valid or invalid");
            }
        }
        //check if the filter is to find specific country with any phone number state (find the customers of specific country)
        else if (!countryCode.equalsIgnoreCase("any") && state.equalsIgnoreCase("any")) {

            if (!countries.containsKey(countryCode)) {
                //throw an exception if countryCode is not listed
                CustomerLogger.log.error("country code (" + countryCode + ") not found");
                throw new NotFoundException("country code (" + countryCode + ") not found");
            }
            List<Customer> customers = customerRepository.findByCountry("(" + countryCode + ")%");
            if (customers.isEmpty()) {
                //throw an exception if no customers found
                CustomerLogger.log.error("No customers found");
                throw new NotFoundException("No customers found");
            }
            customers.forEach(customer -> {
                // validate to all customers by countryCode (find the customers of specific country)
                validateCustomerByCode(countryCode, customerResponseList, customer);
            });
        }
        //check if the filter is to find specific country with specific phone number state (find the customers of specific country and specific phone number state)
        else {
            if (!countries.containsKey(countryCode)) {
                //throw an exception if countryCode is not listed
                CustomerLogger.log.error("country code (" + countryCode + ") not found");
                throw new NotFoundException("country code (" + countryCode + ") not found");
            }
            //check if undefined state entered by the user
            if (state.equalsIgnoreCase("valid") || state.equalsIgnoreCase("invalid")) {
                List<Customer> customers = customerRepository.findByCountry("(" + countryCode + ")%");
                if (customers.isEmpty()) throw new NotFoundException("No customers found");
                validateCustomerByState(state, customerResponseList, customers);
            } else {
                //throw an exception if the state is undefined
                CustomerLogger.log.error("state can't be " + state + " it must be valid or invalid");
                throw new NotAcceptableException("state can't be " + state + " it must be valid or invalid");
            }
        }
        CustomerLogger.log.info("Exit getCustomersByCountryCodeAndState Method of CustomerService Class");
        return customerResponseList;
    }

    private void validateCustomerByState(String state, List<CustomerResponse> customerResponseList, List<Customer> customers) {
        if (state.equalsIgnoreCase("valid")) {
            customers.stream().filter(customer -> customer.getPhone().matches(countriesRegex.get(customer.getPhone().substring(1, 4)))).forEach(customer -> {
                CustomerResponse customerResponse = new CustomerResponse();
                validateCustomerByStateFilter(customer, customerResponse);
                customerResponse.setState("Valid");
                customerResponseList.add(customerResponse);
            });
        } else {
            customers.stream().filter(customer -> !customer.getPhone().matches(countriesRegex.get(customer.getPhone().substring(1, 4)))).forEach(customer -> {
                CustomerResponse customerResponse = new CustomerResponse();
                validateCustomerByStateFilter(customer, customerResponse);
                customerResponse.setState("Invalid");
                customerResponseList.add(customerResponse);
            });
        }
    }

    private void validateCustomerByStateFilter(Customer customer, CustomerResponse customerResponse) {
        String countryCodeOfCurrentCustomer = customer.getPhone().substring(1, 4);
        customerResponse.setName(customer.getName());
        customerResponse.setPhone(customer.getPhone().split(" ")[1]);
        customerResponse.setCountry(countries.get(countryCodeOfCurrentCustomer));
        customerResponse.setCountryCode("+" + countryCodeOfCurrentCustomer);
    }

    private void validateCustomerByCode(String countryCode, List<CustomerResponse> customerResponseList, Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setName(customer.getName());
        customerResponse.setPhone(customer.getPhone().split(" ")[1]);
        customerResponse.setCountry(countries.get(countryCode));
        customerResponse.setCountryCode("+" + countryCode);
        customerResponse.setState(customer.getPhone().matches(countriesRegex.get(countryCode)) ? "Valid" : "Invalid");
        customerResponseList.add(customerResponse);
    }

}
