package com.example.cong.entitis;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class GoodsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int amount;

    private double price;
    private String priceString;

    private int discount;

    private int isActive;

    private double totalPrice;
    private String totalPriceString;



    @ManyToOne()
    @JoinColumn(name = "id_goods")
    private Goods goods;

    @ManyToOne()
    @JoinColumn(name = "id_bill")
    private Bill bill;
}
