package com.study.mongodb.controller;

import com.study.mongodb.model.Customer;
import com.study.mongodb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/findAll")
    public List<Customer> queryAllCustomers(){
        return customerService.queryAllCustomers();
    }

    @PostMapping("/addCustomer")
    public Map<String,Object> addCustomer(Customer customer){
        Map<String,Object> map=new HashMap<>();
        try{
            customerService.addCustomer(customer);
            map.put("code","success");
            map.put("msg","add customer successfully.");
        }catch (Exception e){
            map.put("code","error");
            map.put("msg","add customer failed.");
        }
        return map;
    }
}
