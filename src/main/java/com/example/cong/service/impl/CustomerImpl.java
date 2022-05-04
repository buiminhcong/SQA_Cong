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
        Optional<Customer> optional = customerRepository.findById(id);
        if (optional.isPresent()){
            Customer c = optional.get();
            System.out.println(c.getId());
            return c;
        }else {
            System.out.println("NUll roi");
            return null;

        }

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

        List<Customer> customerList = getCustomerByPhone(customer.getPhone());

        if (customerList.size() == 0) {
            customer.setIsActive(1);
            customer.setTotalCoins(0);
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Override
    public List<Customer> getAllCustomer() {
        List<Customer> customerList = customerRepository.getAllCustomer();
        return customerList;
    }

    @Override
    public boolean edtiCustomer(int id, Customer customer) {

        Optional<Customer> optional = customerRepository.findById(id);
        if(optional.isPresent()){
            if(customer.getPhone().trim() == optional.get().getPhone().trim() ){
                return false;
            }else {
                Customer csm = optional.get();
                csm.setPhone(customer.getPhone());
                csm.setAddress(customer.getAddress());
                csm.setEmail(customer.getEmail());
                csm.setName(customer.getName());
                customerRepository.save(csm);
//                customerRepository.delete(customer);
                return true;
            }
        }else {
            return  false;
        }




    }

    @Override
    public void deleteCustomerById(int id) {
        Customer c = customerRepository.getById(id);
        c.setIsActive(0);
        customerRepository.save(c);
    }

    @Override
    public Customer getOneCustomerByPhone(String phone) {
        Optional<Customer> optional = Optional.ofNullable(customerRepository.getCustomerByPhone(phone));
        if(optional.isPresent()){
            Customer c = optional.get();
            return c ;
        }
        return null;
    }


}
