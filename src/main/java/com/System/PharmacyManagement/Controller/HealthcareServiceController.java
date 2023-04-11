package com.System.PharmacyManagement.Controller;

import com.System.PharmacyManagement.Model.HealthcareService;
import com.System.PharmacyManagement.Model.ResponseObject;
import com.System.PharmacyManagement.Repository.HealthcareServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController // Gọi java springboot
@RequestMapping("/api/v1/backend/HealthcareService") // Địa chỉnh chính (vô nhánh localhost:8080/api/v1 )
public class HealthcareServiceController {

    @Autowired
    private HealthcareServiceRepository repository;

    @GetMapping("get")
    List<HealthcareService> getAllHealthcareServices() {
        return repository.findAll();
        //Using H2 Database to store data locally
        //Send Request Using Postman
    }

    @GetMapping("shallowget")
    List<Map<String, Object>> shallow() {
        List<Map<String, Object>> result = repository.shallowFind();
        List<Map<String, Object>> resultMap = new ArrayList<>();
        for (Map<String, Object> map : result) {
            Map<String, Object> curMap = new LinkedHashMap<>(); // Use a LinkedHashMap to ensure the column order is preserved
            curMap.put("id", map.get("id"));
            curMap.put("name", map.get("name"));
            curMap.put("age", map.get("age"));
            curMap.put("tell", map.get("tell"));
            curMap.put("gender", map.get("gender"));
            curMap.put("loginID", map.get("loginID"));
            resultMap.add(curMap);
        }
        return resultMap;
    }









    @GetMapping("get/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<HealthcareService> foundHealthcareService = repository.findById(id);
        if (foundHealthcareService.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query HealthcareService successfully", foundHealthcareService)
                    //You can replace "ok" with your defined "error code"
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find HealthcareService with id= " + id, "")
            );
        }
    }


    @PostMapping("/insertHealthcareService")
    ResponseEntity<ResponseObject> insertHealthcareService(@RequestBody HealthcareService newHealthcareService) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert HealthcareService Successfully", repository.save(newHealthcareService))
        );
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> checkHealthcareService(@RequestBody HealthcareService newHealthcareService) { //Check if HealthcareServiceID is duplicate or not
        List<HealthcareService> foundHealthcareService = repository.findByid((newHealthcareService.getId())
        );
        if (foundHealthcareService.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "HealthcareService name already taken", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert HealthcareService Successfully", repository.save(newHealthcareService))
        );
    }




    @PostMapping("/insertMultiple")
    ResponseEntity<ResponseObject> insertMultipleHealthcareServices(@RequestBody List<HealthcareService> newHealthcareServices) {
        List<HealthcareService> insertedServices = new ArrayList<>();
        List<String> failedServices = new ArrayList<>();
        for (HealthcareService service : newHealthcareServices) {
            List<HealthcareService> foundServices = repository.findByid(service.getId());
            if (foundServices.size() > 0) {
                failedServices.add("Duplicate ID: " + service.getId());
            } else {
                insertedServices.add(repository.save(service));
            }
        }
        String message = "";
        if (insertedServices.size() > 0) {
            message += "Inserted " + insertedServices.size() + " HealthcareService(s) successfully.";
        }
        if (failedServices.size() > 0) {
            message += " Failed to insert " + failedServices.size() + " HealthcareService(s): " + String.join(", ", failedServices);
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", message, "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", message, insertedServices)
        );
    }


    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateHealthcareService(@RequestBody HealthcareService newHealthcareService, @PathVariable Long id) {
        HealthcareService updatedHealthcareService = repository.findById(id).map(healthcareService -> {
            healthcareService.setId(newHealthcareService.getId());
            healthcareService.setName(newHealthcareService.getName());
            healthcareService.setAge(newHealthcareService.getAge());
            healthcareService.setTell(newHealthcareService.getTell());
            healthcareService.setGender(newHealthcareService.getGender());
            healthcareService.setLogin(newHealthcareService.getLogin());
            return repository.save(healthcareService);
        }).orElseGet(() -> {
            newHealthcareService.setId(id);
            return repository.save(newHealthcareService);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Healthcare Service Successfully", updatedHealthcareService)
        );
    }


    //Delete a HealthcareService => DELETE method
    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteHealthcareService(@PathVariable Long id) {
        boolean exists = repository.existsById(id);
        if(exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete HealthcareService successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find HealthcareService to DELETE", "")
        );
    }

}
