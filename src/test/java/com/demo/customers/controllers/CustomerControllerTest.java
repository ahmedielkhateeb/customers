package com.demo.customers.controllers;

import com.demo.customers.dto.CustomerResponse;
import com.demo.customers.exceptions.custom.NotAcceptableException;
import com.demo.customers.exceptions.custom.NotFoundException;
import com.demo.customers.services.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private CustomerController customerController;


    private CustomerResponse customerNumOneResponse;
    private CustomerResponse customerNumTwoResponse;
    private List<CustomerResponse> customerResponseList;
    private List<CustomerResponse> validCustomerResponseList;
    private List<CustomerResponse> invalidCustomerResponseList;

    @Before
    public void init() {
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

    @Test
    public void getCustomersTest() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerService.getCustomersByCountryCodeAndState("any", "any")).thenReturn(customerResponseList);
        ResponseEntity<?> responseEntity = customerController.getCustomers("any", "any");
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody(), customerResponseList);
    }

    @Test
    public void getCustomersTest2() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerService.getCustomersByCountryCodeAndState("212", "any")).thenReturn(customerResponseList);
        ResponseEntity<?> responseEntity = customerController.getCustomers("212", "any");
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody(), customerResponseList);
    }

    @Test
    public void getCustomersTest3() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerService.getCustomersByCountryCodeAndState("212", "valid")).thenReturn(validCustomerResponseList);
        ResponseEntity<?> responseEntity = customerController.getCustomers("212", "valid");
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody(), validCustomerResponseList);
    }

    @Test
    public void getCustomersTest4() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerService.getCustomersByCountryCodeAndState("212", "invalid")).thenReturn(invalidCustomerResponseList);
        ResponseEntity<?> responseEntity = customerController.getCustomers("212", "invalid");
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody(), invalidCustomerResponseList);
    }

    @Test
    public void getCustomersTest5() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerService.getCustomersByCountryCodeAndState("any", "valid")).thenReturn(validCustomerResponseList);
        ResponseEntity<?> responseEntity = customerController.getCustomers("any", "valid");
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody(), validCustomerResponseList);
    }

    @Test
    public void getCustomersTest6() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerService.getCustomersByCountryCodeAndState("any", "invalid")).thenReturn(invalidCustomerResponseList);
        ResponseEntity<?> responseEntity = customerController.getCustomers("any", "invalid");
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody(), invalidCustomerResponseList);
    }

    @Test(expected = NotFoundException.class)
    public void getCustomersTest7() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerService.getCustomersByCountryCodeAndState("anyy", "invalid")).thenThrow(NotFoundException.class);
        ResponseEntity<?> responseEntity = customerController.getCustomers("anyy", "invalid");
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assert.assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test(expected = NotAcceptableException.class)
    public void getCustomersTest8() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerService.getCustomersByCountryCodeAndState("any", "invaliddd")).thenThrow(NotAcceptableException.class);
        ResponseEntity<?> responseEntity = customerController.getCustomers("any", "invaliddd");
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assert.assertEquals(406, responseEntity.getStatusCodeValue());
    }

    @Test(expected = NotFoundException.class)
    public void getCustomersTest9() throws NotAcceptableException, NotFoundException {
        Mockito.when(customerService.getCustomersByCountryCodeAndState("anyy", "invaliddd")).thenThrow(NotFoundException.class);
        ResponseEntity<?> responseEntity = customerController.getCustomers("anyy", "invaliddd");
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assert.assertEquals(404, responseEntity.getStatusCodeValue());
    }
}