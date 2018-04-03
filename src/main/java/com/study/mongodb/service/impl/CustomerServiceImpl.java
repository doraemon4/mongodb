package com.study.mongodb.service.impl;

import com.study.mongodb.dao.CustomerRepository;
import com.study.mongodb.model.Customer;
import com.study.mongodb.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> queryAllCustomers(){
        return customerRepository.findAll();
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.insert(customer);
    }
}
