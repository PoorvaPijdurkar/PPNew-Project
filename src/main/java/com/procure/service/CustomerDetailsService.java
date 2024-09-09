package com.procure.service;

import com.procure.domain.CustomerDetails;
import com.procure.repository.CustomerDetailsRepository;
import com.procure.web.rest.errors.DuplicateRecordsFoundException;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService {

    private final CustomerDetailsRepository customerDetailsRepository;

    public CustomerDetailsService(CustomerDetailsRepository customerDetailsRepository) {
        this.customerDetailsRepository = customerDetailsRepository;
    }

    public List<CustomerDetails> fetchAllCustomerRecords() {
        return customerDetailsRepository.findAll();
    }

    public CustomerDetails save(CustomerDetails customerDetails) {
        try {
            return customerDetailsRepository.save(customerDetails);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DuplicateRecordsFoundException("Duplicate record error: Either email or mobile number already exists.");
        }
    }

    public void deleteCustomerDetailsById(Long id) {
        if (customerDetailsRepository.existsById(id)) {
            customerDetailsRepository.deleteById(id);
        } else {
            throw new RuntimeException("Customer details not found for given customer id");
        }
    }
}
