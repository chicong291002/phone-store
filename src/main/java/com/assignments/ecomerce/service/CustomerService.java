package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.model.Employee;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    Page<Customer> pageCustomer(int pageNo);

    Page<Customer> searchCustomer(int pageNo, String keyword);
    void blockCustomer(Integer id);
    void unlockCustomer(Integer id);
    Customer findByPhoneAndEmail(String phone, String email);
    Customer save(Customer customer);
    Customer updateStatus(Integer id);
    Customer findById(Integer id);
}