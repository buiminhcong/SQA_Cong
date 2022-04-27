package com.example.cong.repository;

import com.example.cong.entitis.GoodsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GoodItemRepository extends JpaRepository<GoodsItem, Integer> {


    @Transactional
    @Query(value = "select * from goods_item where id_bill = ?1", nativeQuery = true)
    List<GoodsItem> getGoodsItemByIDBill(int id);

}
