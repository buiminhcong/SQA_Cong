package com.example.cong.repository;

import com.example.cong.entitis.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(value = "select * from customer where phone = ?", nativeQuery = true)
    List<Customer> findCustomerByPhone(String phone);

    @Query(value = "SELECT * FROM sqa_cong.customer where is_active = 1", nativeQuery = true)
    List<Customer> getAllCustomer();

}
