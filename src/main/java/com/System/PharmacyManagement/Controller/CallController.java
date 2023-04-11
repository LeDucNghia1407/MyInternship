package com.System.PharmacyManagement.Controller;

import com.System.PharmacyManagement.Model.Call;
import com.System.PharmacyManagement.Model.ResponseObject;
import com.System.PharmacyManagement.Repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController // Gọi java springboot
@RequestMapping("/api/v1/backend/Call") // Địa chỉnh chính (vô nhánh localhost:8080/api/v1 )
public class    CallController {

    @Autowired
    private CallRepository repository;

    @GetMapping("get")
    List<Call> getAllCalls() {
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
            curMap.put("date", map.get("date"));
            curMap.put("health_facility_id", map.get("health_facility_id")); // Use the correct column name here
            curMap.put("healthcare_service_id", map.get("healthcare_service_id"));
            resultMap.add(curMap);
        }
        return resultMap;
    }









    @GetMapping("get/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Call> foundCall = repository.findById(id);
        if (foundCall.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query Call successfully", foundCall)
                    //You can replace "ok" with your defined "error code"
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find Call with id= " + id, "")
            );
        }
    }


    @PostMapping("/insertCall")
    ResponseEntity<ResponseObject> insertCall(@RequestBody Call newCall) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Call Successfully", repository.save(newCall))
        );
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> checkCall(@RequestBody Call newCall) { //Check if CallID is duplicate or not
        List<Call> foundCall = repository.findByid((newCall.getId())
        );
        if (foundCall.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Call name already taken", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Call Successfully", repository.save(newCall))
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateCall(@RequestBody Call newCall, @PathVariable Long id) {
        Call updatedCall = repository.findById(id).map(call -> {
            call.setId(newCall.getId());
            call.setHealthcareServiceId(newCall.getHealthcareServiceId());
            call.setHealthFacilityId(newCall.getHealthFacilityId());
            call.setDate(newCall.getDate());
            return repository.save(call);
        }).orElseGet(() -> {
            newCall.setId(id);
            return repository.save(newCall);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Call Successfully", updatedCall)
        );
    }

    //Delete a Call => DELETE method
    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteCall(@PathVariable Long id) {
        boolean exists = repository.existsById(id);
        if(exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete Call successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find Call to DELETE", "")
        );
    }

}
