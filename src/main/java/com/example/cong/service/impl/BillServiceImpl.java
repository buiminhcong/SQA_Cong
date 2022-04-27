package com.example.cong.service.impl;

import com.example.cong.entitis.Bill;
import com.example.cong.repository.BillRepository;
import com.example.cong.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Override
    public List<Bill> getBills() {
        List<Bill> list = billRepository.findAll();
       return list;
    }

    @Override
    public Bill getBillById(int id) {
        Optional<Bill> optionalBill = billRepository.findById(id);
        return optionalBill.get();
    }

    @Override
    public List<Bill> getBillsByIDCustomer(int id) {
        List<Bill> list = billRepository.getBillByIDCustomer(id);
        return list;
    }

}
