package com.example.cong.controller;

import com.example.cong.entitis.Bill;
import com.example.cong.entitis.Customer;
import com.example.cong.entitis.GoodsItem;
import com.example.cong.entitis.Staff;
import com.example.cong.repository.GoodItemRepository;
import com.example.cong.service.BillService;
import com.example.cong.service.CustomerService;
import com.example.cong.service.GoodItemService;
import com.example.cong.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.NumberFormat;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private GoodItemService goodItemService;

    NumberFormat currentLocale = NumberFormat.getInstance();

    @GetMapping("/list-bill")
    public String getBills(Model model){

        List<Bill> bills = billService.getBills();

        for(int i=0; i<bills.size(); i++){
            double gia = bills.get(i).getActualPrice();
            String g = currentLocale.format(gia);
            bills.get(i).setActualPriceString(g);
        }

        model.addAttribute("listBills", bills);

        return "get-bills";
    }

    @RequestMapping("/view")
    public String billDetail(@RequestParam("id") int id, Model model){

        Bill bill = billService.getBillById(id);

        List<GoodsItem> goodsItems = goodItemService.getListGoodItemByIdBill(id);

        double endPrice = 0;int totalItem = 0;
        double totalPriceItem = 0;
        double sumPriceItem = 0;
        for(int i=0; i<goodsItems.size(); i++){
            GoodsItem g = goodsItems.get(i);
            sumPriceItem += (g.getPrice() - g.getPrice()*g.getDiscount()/100)  * g.getAmount();
            totalItem += g.getAmount();
            totalPriceItem = g.getAmount()*g.getPrice();
            g.setTotalPrice(totalPriceItem);
            String donGia = currentLocale.format(g.getPrice());
            String tonGTien = currentLocale.format(totalPriceItem);
            g.setPriceString(donGia);
            g.setTotalPriceString(tonGTien);
        }
        String billToTal = currentLocale.format(bill.getActualPrice());
        bill.setActualPriceString(billToTal);

        model.addAttribute("bill", bill);
        model.addAttribute("goodItems", goodsItems);

        return "view-detail";
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    @GetMapping("/search")
    public String search( @RequestParam("key") String text, Model model ){

        try {
            String s = text.trim();
            System.out.println("Text: " +  s);
            List<Bill> listBills = new ArrayList<>();
            List<Customer> customerList = new ArrayList<>();

            if(s.equals("")){
                System.out.println("Truong hop 1 s la rong");
                listBills = billService.getBills();
                model.addAttribute("listBills", listBills);
            }else if(s.contains("select") || s.contains("or 1=1")
                    || s.contains(" or") ||  s.contains("where")
                    || s.contains("1=1") || s.contains("or 1=1;–") || s.contains("‘ or ‘abc‘=‘abc‘;–")
                    ||s.contains("‘ or ‘ ‘=‘ ‘;–") || s.contains("%")){
                System.out.println("Truong hop injection");
                System.out.println("Text: " +  s);
                model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
            }else {
                customerList = customerService.getCustomerByPhone(s);
                System.out.println("Dau Vao la so dien thoai");
                if(customerList.size() == 0 ){
                    System.out.println("So dien thoai sai");
                    model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
                }else {
                    System.out.println("Co ban ghi");
                    Customer c = customerList.get(0);
                    listBills = billService.getBillsByIDCustomer(c.getId());
                    model.addAttribute("listBills", listBills);
                }

            }

            return "get-bills";

        }catch (Exception e){
            System.out.println("Loi Parser");
            model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
            return "get-bills";
        }

    }

//    @GetMapping("/search")
//    public String search( @RequestParam("key") String text, Model model ){
//
//        try {
//            String s = text.trim();
//            System.out.println("Text: " +  s);
//            List<Bill> listBills = new ArrayList<>();
//
//            if(s.equals("")){
//                System.out.println("Truong hop 1 s la rong");
//                listBills = billService.getBills();
//                model.addAttribute("listBills", listBills);
//            }else if(!isNumeric(s) && !s.equals("")){
//                System.out.println("Truongf hơp 2 s khong phai la so");
//                model.addAttribute("notify", "Dữ liệu không khớp, " +
//                        "hoặc không tồn tại, vui lòng thử lại!");
//            }else {
//                System.out.println("s khac rong va s la so");
//                System.out.println(s);
//                int a = Integer.parseInt(s);
//                Bill bill = billService.getBillById(a);
//                if(bill != null){
//                    System.out.println("Co ban ghi");
//                    System.out.println(bill.getId());
//                    listBills.add(bill);
//                    model.addAttribute("listBills", listBills);
//                }else {
//                    System.out.println("Khong co ban ghi");
//
//                    model.addAttribute("notify", "Dữ liệu không khớp, " +
//                            "hoặc không tồn tại, vui lòng thử lại!");
//                }
//
//            }
//
//            return "get-bills";
//
//        }catch (Exception e){
//            System.out.println("Loi Parser");
//            model.addAttribute("notify", "Dữ liệu không khớp, hoặc không tồn tại, vui lòng thử lại!");
//            return "get-bills";
//        }
//
//    }

}
