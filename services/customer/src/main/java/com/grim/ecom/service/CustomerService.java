package com.grim.ecom.service;


import com.grim.ecom.dto.CustomerRequest;
import com.grim.ecom.dto.CustomerResponse;
import com.grim.ecom.exception.CustomerNotFoundException;
import com.grim.ecom.mapper.CustomerMapper;
import com.grim.ecom.model.Customer;
import com.grim.ecom.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest customer) {

        var newCustomer = customerRepository.save(customerMapper.toCustomer(customer));

        return newCustomer.getId();
    }


    public void updateCustomer(@Valid CustomerRequest customer) {
        var existingCustomer = customerRepository.findById(customer.id())
                .orElseThrow(() -> new CustomerNotFoundException
                        (format("Cannot update customer:: No customer found with the provided id:: %s",customer.id())));

        mergerCustomer(existingCustomer, customer);

        customerRepository.save(existingCustomer);
    }

    private void mergerCustomer(Customer existingCustomer, @Valid CustomerRequest customer) {
        if(StringUtils.isNotEmpty(customer.firstName())) {
            existingCustomer.setFirstName(customer.firstName());
        }
        if(StringUtils.isNotEmpty(customer.lastName())) {
            existingCustomer.setLastName(customer.lastName());
        }
        if(StringUtils.isNotEmpty(customer.email())) {
            existingCustomer.setEmail(customer.email());
        }
        if(customer.address() != null) {
            existingCustomer.setAddress(customer.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return customerRepository.findById(customerId)
                .isPresent();
    }

    public CustomerResponse findCustomerById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException
                        (format("No customer found with the provided id:: %s",customerId)));
    }

    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
