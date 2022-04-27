package com.example.cong.controller;

import com.example.cong.entitis.Bill;
import com.example.cong.entitis.Customer;
import com.example.cong.service.CustomerService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {


    @Autowired
    private CustomerService customerService;

    @GetMapping("/list-customer")
    public String getCustomers(Model model) {

        List<Customer> customerList = new ArrayList<>();
        customerList = customerService.getAllCustomer();
        model.addAttribute("listCustomer", customerList);

        return "get-customers";
    }

    @GetMapping("/add")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "add-customer";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("customer") Customer customer, Errors errors, Model model) {


        if (errors.hasErrors()) {
            System.out.println(errors.getObjectName()
            );

            return "add-customer";
        } else {
            List<Customer> customerList = customerService.getCustomerByPhone(customer.getPhone());
            System.out.println(customerList.size());
            if (customerList.size() == 0) {
                customer.setIsActive(1);
                customer.setTotalCoins(0);
                customerService.saveCustoemr(customer);
                return "redirect:/customer/list-customer";
            } else {
                System.out.println("Cong");
                model.addAttribute("f", "Đã có khách hàng sử dụng số điện thoại trên!");
                return "add-customer";
            }
        }
    }

    @RequestMapping("/edit")
    public String editCustomer(@RequestParam("id") int id, Model model) {
        System.out.println(id);
        Customer c = customerService.getCustomerById(id);
        model.addAttribute("customer", c);
        return "edit-customer";
    }

    @PostMapping("/save-edit")
    public String saveEdit(@Valid @ModelAttribute("customer") Customer customer, Errors errors, Model model) {
        System.out.println(customer.getName());
        if (errors.hasErrors()) {
            System.out.println("co loi");
            return "edit-customer";
        } else {
            System.out.println(customer.getId() + " " + customer.getName());
            System.out.println("Luu Thanh cong");

            List<Customer> customerList = customerService.getCustomerByPhone(customer.getPhone());
            if (customerList.size() >= 2) {
                System.out.println("Cong");
                model.addAttribute("f", "Đã có khách hàng sử dụng số điện thoại trên!");
                return "add-customer";
            } else {
                customerService.edtiCustomer(customer.getId(), customer);
                return "redirect:/customer/list-customer";
            }

        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        customerService.deleteCustomerById(id);
        return "redirect:/customer/list-customer";
    }

    @GetMapping("/search")
    public String search(@RequestParam("key") String text, Model model) {

        try {
            String s = text.trim();
            System.out.println("Text: " + s);
            List<Bill> listBills = new ArrayList<>();
            List<Customer> customerList = new ArrayList<>();

            if (s.equals("")) {
                System.out.println("Truong hop 1 s la rong");
                customerList = customerService.getAllCustomer();
                model.addAttribute("listCustomer", customerList);
            } else if (s.contains("select") || s.contains("or 1=1")
                    || s.contains(" or") || s.contains("where")
                    || s.contains("1=1") || s.contains("or 1=1;–") || s.contains("‘ or ‘abc‘=‘abc‘;–")
                    || s.contains("‘ or ‘ ‘=‘ ‘;–") || s.contains("%")) {
                System.out.println("Truong hop injection");
                System.out.println("Text: " + s);
                model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
            } else {
                customerList = customerService.getCustomerByPhone(s);
                System.out.println("Size list: " + customerList.size());
                if (customerList.size() == 0) {
                    model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
                } else {
                    model.addAttribute("listCustomer", customerList);
                }
            }
            return "get-customers";

        } catch (Exception e) {
            System.out.println("Loi Parser");
            model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
            return "get-customers";
        }

    }

}
