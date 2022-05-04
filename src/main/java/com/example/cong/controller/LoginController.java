package com.example.cong.controller;

import com.example.cong.entitis.Staff;
import com.example.cong.repository.StaffRepository;
import com.example.cong.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("/login")
    public String login(Model model, HttpSession session){
        Staff staff = new Staff();
        model.addAttribute("staff", staff);
        String s = (String) session.getAttribute("incorect");
        session.removeAttribute("incorect");
        model.addAttribute("incorect", s);
        System.out.println("done");
        return "login";
    }

    @GetMapping("/home-staff")
    public String Home(){
        return "home-staff";
    }


    @PostMapping("/home-staff")
    public String HomeStaff(@ModelAttribute("staff") Staff staff,  Model model,
                            HttpSession session){

        String us = staff.getUsername();
        String ps = staff.getPassword();

        Boolean check = staffService.checkAcount(us, ps);
        Staff s = staffService.getStaffByUserNameAndPassword(us, ps);

        System.out.println(check);
        if(check == true){
            session.setAttribute("staff", s);
            return "home-staff";
        }else {
            session.setAttribute("incorect", "Tài khoản hoặc mật khẩu không khớp!");
            return "redirect:login";
        }

    }
    @GetMapping("/logout")
    public String logout(HttpSession session, Model model){

        Staff s = new Staff();
        model.addAttribute("staff", s);
        session.removeAttribute("staff");

        return "redirect:login";
    }

}
