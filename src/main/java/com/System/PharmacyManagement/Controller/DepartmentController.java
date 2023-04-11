package com.System.PharmacyManagement.Controller;

import com.System.PharmacyManagement.Model.Department;
import com.System.PharmacyManagement.Model.ResponseObject;
import com.System.PharmacyManagement.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController // Gọi java springboot
@RequestMapping("/api/v1/backend/Department") // Địa chỉnh chính (vô nhánh localhost:8080/api/v1 )
public class DepartmentController {

    @Autowired
    private DepartmentRepository repository;

    @GetMapping("get")
    List<Department> getAllDepartments() {
        return repository.findAll();
        //Using H2 Database to store data loDepartmenty
        //Send Request Using Postman
    }

    @GetMapping("shallowget")
    List<Map<String, Object>> shallow() {
        List<Map<String, Object>> result = repository.shallowFind();
        List<Map<String, Object>> resultMap = new ArrayList<>();
        for (Map<String, Object> map : result) {
            Map<String, Object> curMap = new LinkedHashMap<>(); // Use a LinkedHashMap to ensure the column order is preserved
            curMap.put("id", map.get("id"));
            curMap.put("doctorName", map.get("doctorName"));
            curMap.put("name", map.get("name"));
            curMap.put("status", map.get("status"));
            curMap.put("healthfacilityID", map.get("healthfacilityID"));
            resultMap.add(curMap);
        }
        return resultMap;
    }









    @GetMapping("get/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Department> foundDepartment = repository.findById(id);
        if (foundDepartment.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query Department successfully", foundDepartment)
                    //You can replace "ok" with your defined "error code"
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find Department with id= " + id, "")
            );
        }
    }


    @PostMapping("/insertDepartment")
    ResponseEntity<ResponseObject> insertDepartment(@RequestBody Department newDepartment) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Department Successfully", repository.save(newDepartment))
        );
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> checkDepartment(@RequestBody Department newDepartment) { //Check if DepartmentID is duplicate or not
        List<Department> foundDepartment = repository.findByid((newDepartment.getId())
        );
        if (foundDepartment.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Department name already taken", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Department Successfully", repository.save(newDepartment))
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateDepartment(@RequestBody Department newDepartment, @PathVariable Long id) {
        Department updatedDepartment = repository.findById(id).map(department -> {
            department.setId(newDepartment.getId());
            department.setHealthFacility(newDepartment.getHealthFacility());
            department.setName(newDepartment.getName());
            department.setDoctorName(newDepartment.getDoctorName());
            department.setStatus(newDepartment.getStatus());
            return repository.save(department);
        }).orElseGet(() -> {
            newDepartment.setId(id);
            return repository.save(newDepartment);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Department Successfully", updatedDepartment)
        );
    }

    //Delete a Department => DELETE method
    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteDepartment(@PathVariable Long id) {
        boolean exists = repository.existsById(id);
        if(exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete Department successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find Department to DELETE", "")
        );
    }

}
