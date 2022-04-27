package com.example.cong.repository;

import com.example.cong.entitis.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

    @Query(value = "select * from bill where id_customer = ?", nativeQuery = true)
    List<Bill> getBillByIDCustomer(int id);

}
