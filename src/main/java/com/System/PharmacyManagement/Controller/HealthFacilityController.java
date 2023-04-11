package com.System.PharmacyManagement.Controller;

import com.System.PharmacyManagement.Model.HealthFacility;
import com.System.PharmacyManagement.Model.ResponseObject;
import com.System.PharmacyManagement.Repository.HealthFacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController // Gọi java springboot
@RequestMapping("/api/v1/backend/HealthFacility") // Địa chỉnh chính (vô nhánh localhost:8080/api/v1 )
public class HealthFacilityController {

    @Autowired
    private HealthFacilityRepository repository;

    @GetMapping("get")
    List<HealthFacility> getAllHealthFacilitys() {
        return repository.findAll();
        //Using H2 Database to store data loHealthFacilityy
        //Send Request Using Postman
    }

//    @GetMapping("shallowget")
//    List<Map<String, Object>> shallow() {
//        List<Map<String, Object>> result = repository.shallowFind();
//        List<Map<String, Object>> resultMap = new ArrayList<>();
//        for (Map<String, Object> map : result) {
//            Map<String, Object> curMap = new LinkedHashMap<>(); // Use a LinkedHashMap to ensure the column order is preserved
//            curMap.put("id", map.get("id"));
//            curMap.put("name", map.get("name"));
//            curMap.put("manufactureDate", map.get("manufactureDate")); // Use the correct column name here
//            curMap.put("expiredDate", map.get("expiredDate"));
//            curMap.put("usages", map.get("usages"));
//            curMap.put("type", map.get("type"));
//            curMap.put("price", map.get("price"));
//            curMap.put("ManagerName", map.get("ManagerName"));
//            resultMap.add(curMap);
//        }
//        return resultMap;
//    }








    @GetMapping("get/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<HealthFacility> foundHealthFacility = repository.findById(id);
        if (foundHealthFacility.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query HealthFacility successfully", foundHealthFacility)
                    //You can replace "ok" with your defined "error code"
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find HealthFacility with id= " + id, "")
            );
        }
    }


    @PostMapping("/insertHealthFacility")
    ResponseEntity<ResponseObject> insertHealthFacility(@RequestBody HealthFacility newHealthFacility) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert HealthFacility Successfully", repository.save(newHealthFacility))
        );
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> checkHealthFacility(@RequestBody HealthFacility newHealthFacility) { //Check if HealthFacilityID is duplicate or not
        List<HealthFacility> foundHealthFacility = repository.findByid((newHealthFacility.getId())
        );
        if (foundHealthFacility.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "HealthFacility name already taken", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert HealthFacility Successfully", repository.save(newHealthFacility))
        );
    }

    // PUT mapping for HealthFacility class
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateHealthFacility(@RequestBody HealthFacility newHealthFacility, @PathVariable Long id) {
        HealthFacility updatedHealthFacility = repository.findById(id).map(healthFacility -> {
            healthFacility.setId(newHealthFacility.getId());
            healthFacility.setName(newHealthFacility.getName());
            healthFacility.setAddress(newHealthFacility.getAddress());
            healthFacility.setSize(newHealthFacility.getSize());
            return repository.save(healthFacility);
        }).orElseGet(() -> {
            newHealthFacility.setId(id);
            return repository.save(newHealthFacility);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Health Facility Successfully", updatedHealthFacility)
        );
    }

    //Delete a HealthFacility => DELETE method
    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteHealthFacility(@PathVariable Long id) {
        boolean exists = repository.existsById(id);
        if(exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete HealthFacility successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find HealthFacility to DELETE", "")
        );
    }

}
