package com.example.cong.service;

import com.example.cong.entitis.Bill;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BillService {

    List<Bill> getBills();

    Bill getBillById(int id);

    List<Bill> getBillsByIDCustomer(int id);

}
