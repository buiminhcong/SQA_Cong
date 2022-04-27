package com.example.cong.service;

import com.example.cong.entitis.GoodsItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GoodItemService {

    List<GoodsItem> getListGoodItemByIdBill(int id);

}
