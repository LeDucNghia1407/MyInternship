package com.System.PharmacyManagement.Controller;

import com.System.PharmacyManagement.Model.HealthcareStaff;
import com.System.PharmacyManagement.Model.ResponseObject;
import com.System.PharmacyManagement.Repository.HealthcareStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController // Gọi java springboot
@RequestMapping("/api/v1/backend/HealthcareStaff") // Địa chỉnh chính (vô nhánh localhost:8080/api/v1 )
public class HealthcareStaffController {

    @Autowired
    private HealthcareStaffRepository repository;

    @GetMapping("get")
    List<HealthcareStaff> getAllHealthcareStaffs() {
        return repository.findAll();
        //Using H2 Database to store data loHealthcareStaffy
        //Send Request Using Postman
    }

    @GetMapping("shallowget")
    List<Map<String, Object>> shallow() {
        List<Map<String, Object>> result = repository.shallowFind();
        List<Map<String, Object>> resultMap = new ArrayList<>();
        for (Map<String, Object> map : result) {
            Map<String, Object> curMap = new LinkedHashMap<>(); // Use a LinkedHashMap to ensure the column order is preserved
            curMap.put("id", map.get("id"));
            curMap.put("doctorId", map.get("doctorId"));
            curMap.put("type", map.get("type"));
            curMap.put("roomid", map.get("roomid"));
            resultMap.add(curMap);
        }
        return resultMap;
    }









    @GetMapping("get/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<HealthcareStaff> foundHealthcareStaff = repository.findById(id);
        if (foundHealthcareStaff.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query HealthcareStaff successfully", foundHealthcareStaff)
                    //You can replace "ok" with your defined "error code"
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find HealthcareStaff with id= " + id, "")
            );
        }
    }


    @PostMapping("/insertHealthcareStaff")
    ResponseEntity<ResponseObject> insertHealthcareStaff(@RequestBody HealthcareStaff newHealthcareStaff) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert HealthcareStaff Successfully", repository.save(newHealthcareStaff))
        );
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> checkHealthcareStaff(@RequestBody HealthcareStaff newHealthcareStaff) { //Check if HealthcareStaffID is duplicate or not
        List<HealthcareStaff> foundHealthcareStaff = repository.findByid((newHealthcareStaff.getId())
        );
        if (foundHealthcareStaff.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "HealthcareStaff name already taken", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert HealthcareStaff Successfully", repository.save(newHealthcareStaff))
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateHealthcareStaff(@RequestBody HealthcareStaff newHealthcareStaff, @PathVariable Long id) {
        HealthcareStaff updatedHealthcareStaff = repository.findById(id).map(healthcareStaff -> {
            healthcareStaff.setId(newHealthcareStaff.getId());
            healthcareStaff.setRoom(newHealthcareStaff.getRoom());
            healthcareStaff.setDoctorId(newHealthcareStaff.getDoctorId());
            healthcareStaff.setType(newHealthcareStaff.getType());
            return repository.save(healthcareStaff);
        }).orElseGet(() -> {
            newHealthcareStaff.setId(id);
            return repository.save(newHealthcareStaff);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Healthcare Staff Successfully", updatedHealthcareStaff)
        );
    }

    //Delete a HealthcareStaff => DELETE method
    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteHealthcareStaff(@PathVariable Long id) {
        boolean exists = repository.existsById(id);
        if(exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete HealthcareStaff successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find HealthcareStaff to DELETE", "")
        );
    }

}
