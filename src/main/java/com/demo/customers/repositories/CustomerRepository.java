package com.demo.customers.repositories;

import com.demo.customers.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(value = "SELECT * FROM 'customer' where phone LIKE :countryCode", nativeQuery = true)
    List<Customer> findByCountry(@Param("countryCode") String countryCode);

}
