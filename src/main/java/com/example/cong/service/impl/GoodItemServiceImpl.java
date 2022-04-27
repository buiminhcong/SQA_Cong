package com.example.cong.service.impl;

import com.example.cong.entitis.GoodsItem;
import com.example.cong.repository.GoodItemRepository;
import com.example.cong.service.GoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodItemServiceImpl implements GoodItemService {

    @Autowired
    private GoodItemRepository goodItemRepository;

    @Override
    public List<GoodsItem> getListGoodItemByIdBill(int id) {
        List<GoodsItem> list = goodItemRepository.getGoodsItemByIDBill(id);
        System.out.println("Tim kiem xong");
        return list;
    }
}
