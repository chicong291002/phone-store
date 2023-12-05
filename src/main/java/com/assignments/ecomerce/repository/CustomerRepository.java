package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.model.Employee;
import com.assignments.ecomerce.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    @Query("SELECT c from Customer c where statusCustomer = 1")
    Page<Customer> pageCustomer(Pageable pageable);

    @Query("SELECT c from Customer c where CONCAT(c.fullname,c.address,c.phone,c.email) like %?1%")
    List<Customer> searchCustomer(String keyword);

    @Query(value = "SELECT c from Customer c WHERE c.statusCustomer = 1 and c.phone = ?1 OR c.email = ?2")
    Customer findByPhoneAndEmail(String phone, String email);
}