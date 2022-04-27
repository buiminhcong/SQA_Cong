package com.example.cong.entitis;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double totalPrice;
    private Date date;
    private int discount;
    private double actualPrice;
    private String actualPriceString;

    private double priceCustomer;
    private String priceCustomerString;

    private double priceBack;
    private String priceBackString;

    private double coinsPay;
    private int isActive;
    private int totalItem;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_staff")
    private Staff staff;




}
