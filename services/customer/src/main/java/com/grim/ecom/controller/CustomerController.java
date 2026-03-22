package com.grim.ecom.controller;

import com.grim.ecom.dto.CustomerRequest;
import com.grim.ecom.dto.CustomerResponse;
import com.grim.ecom.model.Customer;
import com.grim.ecom.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<String> createCustomer
            (@RequestBody @Valid CustomerRequest customer){

        return ResponseEntity.ok(customerService.createCustomer(customer));

    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer
            (@RequestBody @Valid CustomerRequest customer){
            customerService.updateCustomer(customer);
            return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll(){
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existsById
            (@PathVariable("customer-id") String customerId){
        return ResponseEntity.ok(customerService.existsById(customerId));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable("customer-id")  String customerId){
        return ResponseEntity.ok(customerService.findCustomerById(customerId));
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> deleteById(@PathVariable("customer-id")  String customerId){
         customerService.deleteCustomer(customerId);
         return  ResponseEntity.accepted().build();
    }
}
