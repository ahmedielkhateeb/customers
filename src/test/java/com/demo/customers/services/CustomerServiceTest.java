package com.demo.customers.services;

import com.demo.customers.dto.CustomerResponse;
import com.demo.customers.exceptions.custom.NotAcceptableException;
import com.demo.customers.exceptions.custom.NotFoundException;
import com.demo.customers.models.Customer;
import com.demo.customers.repositories.CustomerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class CustomerServiceTest {
    @Mock
    CustomerRepository customerRepository;
    @InjectMocks
    CustomerService customerService;

    private Customer customerNumOne;
    private Customer customerNumTwo;
    private List<Customer> customerList;
    private CustomerResponse customerNumOneResponse;
    private CustomerResponse customerNumTwoResponse;
    private List<CustomerResponse> customerResponseList;
    private List<CustomerResponse> validCustomerResponseList;
    private List<CustomerResponse> invalidCustomerResponseList;


    @Before
    public void init() {
        customerNumOne = new Customer();
        customerNumOne.setName("Walid Hammadi");
        customerNumOne.setPhone("(212) 6007989253");
        customerNumTwo = new Customer();
        customerNumTwo.setName("Yosaf Karrouch");
        customerNumTwo.setPhone("(212) 698054317");
        customerList = new ArrayList<>();
        customerList.add(customerNumOne);
        customerList.add(customerNumTwo);
        customerNumOneResponse = new CustomerResponse();
        customerNumOneResponse.setCountry("Morocco");
        customerNumOneResponse.setState("Invalid");
        customerNumOneResponse.setCountryCode("+212");
        customerNumOneResponse.setPhone("6007989253");
        customerNumOneResponse.setName("Walid Hammadi");
        customerNumTwoResponse = new CustomerResponse();
        customerNumTwoResponse.setCountry("Morocco");
        customerNumTwoResponse.setState("Valid");
        customerNumTwoResponse.setCountryCode("+212");
        customerNumTwoResponse.setPhone("698054317");
        customerNumTwoResponse.setName("Yosaf Karrouch");
        customerResponseList = new ArrayList<>();
        customerResponseList.add(customerNumOneResponse);
        customerResponseList.add(customerNumTwoResponse);
        validCustomerResponseList = new ArrayList<>();
        validCustomerResponseList.add(customerNumTwoResponse);
        invalidCustomerResponseList = new ArrayList<>();
        invalidCustomerResponseList.add(customerNumOneResponse);
    }

    // test for checking if the filter is to find any country with any phone number state (find all the customers)
    @Test
    public void getCustomersByCountryCodeAndStateTest1() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerRepository.findAll()).thenReturn(customerList);
        List<CustomerResponse> customerResponseListTest = customerService.getCustomersByCountryCodeAndState("any", "any");
        Assert.assertEquals(customerResponseList, customerResponseListTest);
    }

    //test for checking if the filter is to find any country with specific phone number state (find the customers of valid  phone numbers)
    @Test
    public void getCustomersByCountryCodeAndStateTest2() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerRepository.findAll()).thenReturn(customerList);
        List<CustomerResponse> customerResponseListTest = customerService.getCustomersByCountryCodeAndState("any", "valid");
        Assert.assertEquals(validCustomerResponseList, customerResponseListTest);
    }

    //test for checking if the filter is to find any country with specific phone number state (find the customers of invalid phone numbers)
    @Test
    public void getCustomersByCountryCodeAndStateTest3() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerRepository.findAll()).thenReturn(customerList);
        List<CustomerResponse> customerResponseListTest = customerService.getCustomersByCountryCodeAndState("any", "invalid");
        Assert.assertEquals(invalidCustomerResponseList, customerResponseListTest);
    }

    //test for checking if the filter is to find specific country with any phone number state (find the customers of specific country)
    @Test
    public void getCustomersByCountryCodeAndStateTest4() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerRepository.findByCountry("(212)%")).thenReturn(customerList);
        List<CustomerResponse> customerResponseListTest = customerService.getCustomersByCountryCodeAndState("212", "any");
        Assert.assertEquals(customerResponseList, customerResponseListTest);
    }

    //test for checking if the filter is to find specific country with specific phone number state (find the customers of specific country and specific phone number state)
    @Test
    public void getCustomersByCountryCodeAndStateTest5() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerRepository.findByCountry("(212)%")).thenReturn(customerList);
        List<CustomerResponse> customerResponseListTest = customerService.getCustomersByCountryCodeAndState("212", "valid");
        Assert.assertEquals(validCustomerResponseList, customerResponseListTest);
    }

    //test for checking if the filter is to find specific country with specific phone number state (find the customers of specific country and specific phone number state)
    @Test
    public void getCustomersByCountryCodeAndStateTest6() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerRepository.findByCountry("(212)%")).thenReturn(customerList);
        List<CustomerResponse> customerResponseListTest = customerService.getCustomersByCountryCodeAndState("212", "invalid");
        Assert.assertEquals(invalidCustomerResponseList, customerResponseListTest);
    }

    // test for checking if no customers found
    // test for checking if the filter is to find any country with any phone number state (find all the customers)
    @Test(expected = NotFoundException.class)
    public void getCustomersByCountryCodeAndStateTest7() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        List<CustomerResponse> customerResponseListTest = customerService.getCustomersByCountryCodeAndState("any", "any");
    }

    //test for checking if the filter is to find any country with specific phone number state (find the customers of valid  phone numbers)
    @Test(expected = NotFoundException.class)
    public void getCustomersByCountryCodeAndStateTest8() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        List<CustomerResponse> customerResponseListTest = customerService.getCustomersByCountryCodeAndState("any", "valid");
    }

    //test for checking if the filter is to find any country with specific phone number state (find the customers of invalid phone numbers)
    @Test(expected = NotFoundException.class)
    public void getCustomersByCountryCodeAndStateTest9() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        List<CustomerResponse> customerResponseListTest = customerService.getCustomersByCountryCodeAndState("any", "invalid");
    }

    //test for checking if the filter is to find specific country with any phone number state (find the customers of specific country)
    @Test(expected = NotFoundException.class)
    public void getCustomersByCountryCodeAndStateTest10() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerRepository.findByCountry("(212)%")).thenReturn(new ArrayList<>());
        List<CustomerResponse> customerResponseListTest = customerService.getCustomersByCountryCodeAndState("212", "any");
    }

    //test for checking if the filter is to find specific country with specific phone number state (find the customers of specific country and specific phone number state)
    @Test(expected = NotFoundException.class)
    public void getCustomersByCountryCodeAndStateTest11() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerRepository.findByCountry("(212)%")).thenReturn(new ArrayList<>());
        List<CustomerResponse> customerResponseListTest = customerService.getCustomersByCountryCodeAndState("212", "valid");
    }

    //test for checking if the filter is to find specific country with specific phone number state (find the customers of specific country and specific phone number state)
    @Test(expected = NotFoundException.class)
    public void getCustomersByCountryCodeAndStateTest12() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerRepository.findByCountry("(212)%")).thenReturn(new ArrayList<>());
        List<CustomerResponse> customerResponseListTest = customerService.getCustomersByCountryCodeAndState("212", "invalid");
    }

    //test for checking if countryCode or state unlisted
    //test for checking unlisted state
    @Test(expected = NotAcceptableException.class)
    public void getCustomersByCountryCodeAndStateTest13() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerRepository.findAll()).thenReturn(customerList);
        List<CustomerResponse> customerResponseListTest = customerService.getCustomersByCountryCodeAndState("any", "validddd");
    }

    //test for checking unlisted countryCode
    @Test(expected = NotFoundException.class)
    public void getCustomersByCountryCodeAndStateTest14() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerRepository.findAll()).thenReturn(customerList);
        List<CustomerResponse> customerResponseListTest = customerService.getCustomersByCountryCodeAndState("418", "any");
    }

    //test for checking unlisted countryCode and countryCode
    @Test(expected = NotFoundException.class)
    public void getCustomersByCountryCodeAndStateTest15() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerRepository.findAll()).thenReturn(customerList);
        List<CustomerResponse> customerResponseListTest = customerService.getCustomersByCountryCodeAndState("418", "validddd");
    }
}