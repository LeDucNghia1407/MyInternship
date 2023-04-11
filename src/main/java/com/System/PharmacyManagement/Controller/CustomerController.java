package com.System.PharmacyManagement.Controller;

import com.System.PharmacyManagement.Model.Customer;
import com.System.PharmacyManagement.Model.ResponseObject;
import com.System.PharmacyManagement.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController // Gọi java springboot
@RequestMapping("/api/v1/backend/Customer") // Địa chỉnh chính (vô nhánh localhost:8080/api/v1 )
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    @GetMapping("get")
    List<Customer> getAllCustomers() {
        return repository.findAll();
        //Using H2 Database to store data loCustomery
        //Send Request Using Postman
    }

    @GetMapping("shallowget")
    List<Map<String, Object>> shallow() {
        List<Map<String, Object>> result = repository.shallowFind();
        List<Map<String, Object>> resultMap = new ArrayList<>();
        for (Map<String, Object> map : result) {
            Map<String, Object> curMap = new LinkedHashMap<>(); // Use a LinkedHashMap to ensure the column order is preserved
            curMap.put("id", map.get("id"));
            curMap.put("age", map.get("age"));
            curMap.put("contact_type", map.get("contact_type"));
            curMap.put("gender", map.get("gender"));
            curMap.put("name", map.get("name"));
            curMap.put("symptom", map.get("symptom"));
            curMap.put("tel", map.get("tel"));
            curMap.put("healthcareServiceId", map.get("healthcareServiceId"));
            resultMap.add(curMap);
        }
        return resultMap;
    }









    @GetMapping("get/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Customer> foundCustomer = repository.findById(id);
        if (foundCustomer.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query Customer successfully", foundCustomer)
                    //You can replace "ok" with your defined "error code"
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find Customer with id= " + id, "")
            );
        }
    }


    @PostMapping("/insertCustomer")
    ResponseEntity<ResponseObject> insertCustomer(@RequestBody Customer newCustomer) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Customer Successfully", repository.save(newCustomer))
        );
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> checkCustomer(@RequestBody Customer newCustomer) { //Check if CustomerID is duplicate or not
        List<Customer> foundCustomer = repository.findByid((newCustomer.getId())
        );
        if (foundCustomer.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Customer name already taken", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Customer Successfully", repository.save(newCustomer))
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateCustomer(@RequestBody Customer newCustomer, @PathVariable Long id) {
        Customer updatedCustomer = repository.findById(id).map(customer -> {
            customer.setId(newCustomer.getId());
            customer.setHealthcareServiceId(newCustomer.getHealthcareServiceId());
            customer.setAge(newCustomer.getAge());
            customer.setTel(newCustomer.getTel());
            customer.setGender(newCustomer.getGender());
            customer.setName(newCustomer.getName());
            customer.setSymptom(newCustomer.getSymptom());
            customer.setContactType(newCustomer.getContactType());
            return repository.save(customer);
        }).orElseGet(() -> {
            newCustomer.setId(id);
            return repository.save(newCustomer);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Customer Successfully", updatedCustomer)
        );
    }

    //Delete a Customer => DELETE method
    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteCustomer(@PathVariable Long id) {
        boolean exists = repository.existsById(id);
        if(exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete Customer successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find Customer to DELETE", "")
        );
    }

}
