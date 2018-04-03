package com.study.mongodb.service;

import com.study.mongodb.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> queryAllCustomers();
    Customer addCustomer(Customer customer);
}
