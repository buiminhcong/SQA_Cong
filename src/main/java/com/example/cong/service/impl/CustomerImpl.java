package com.example.cong.service.impl;

import com.example.cong.entitis.Customer;
import com.example.cong.repository.CustomerRepository;
import com.example.cong.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomerById(int id) {
        return customerRepository.getById(id);
    }

    @Override
    public List<Customer> getCustomerByPhone(String phone) {
        List<Customer> list = customerRepository.findCustomerByPhone(phone);
        return list;
    }

    @Override
    public List<Customer> getCustomers() {
        List<Customer> list = customerRepository.findAll();
        return list;
    }

    @Override
    public Boolean saveCustoemr(Customer customer) {
        customerRepository.save(customer);
        return null;
    }

    @Override
    public List<Customer> getAllCustomer() {
        List<Customer> customerList = customerRepository.getAllCustomer();
        return customerList;
    }

    @Override
    public void edtiCustomer(int id, Customer customer) {

        Optional<Customer> optional = customerRepository.findById(id);
        Customer c = optional.get();
        c.setName(customer.getName());
        c.setPhone(customer.getPhone());
        c.setEmail(customer.getEmail());
        c.setAddress(customer.getAddress());
        c.setIsActive(1);
        c.setTotalCoins(c.getTotalCoins());
        customerRepository.save(c);
    }

    @Override
    public void deleteCustomerById(int id) {
        Customer c = customerRepository.getById(id);
        customerRepository.delete(c);
    }


}
