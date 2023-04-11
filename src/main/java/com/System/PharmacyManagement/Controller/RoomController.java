package com.System.PharmacyManagement.Controller;

import com.System.PharmacyManagement.Model.Room;
import com.System.PharmacyManagement.Model.ResponseObject;
import com.System.PharmacyManagement.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController // Gọi java springboot
@RequestMapping("/api/v1/backend/Room") // Địa chỉnh chính (vô nhánh localhost:8080/api/v1 )
public class RoomController {

    @Autowired
    private RoomRepository repository;

    @GetMapping("get")
    List<Room> getAllRooms() {
        return repository.findAll();
        //Using H2 Database to store data loRoomy
        //Send Request Using Postman
    }

    @GetMapping("shallowget")
    List<Map<String, Object>> shallow() {
        List<Map<String, Object>> result = repository.shallowFind();
        List<Map<String, Object>> resultMap = new ArrayList<>();
        for (Map<String, Object> map : result) {
            Map<String, Object> curMap = new LinkedHashMap<>(); // Use a LinkedHashMap to ensure the column order is preserved
            curMap.put("id", map.get("id"));
            curMap.put("room", map.get("room"));
            curMap.put("status", map.get("status"));
            curMap.put("departmentid", map.get("departmentid"));
            resultMap.add(curMap);
        }
        return resultMap;
    }









    @GetMapping("get/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Room> foundRoom = repository.findById(id);
        if (foundRoom.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query Room successfully", foundRoom)
                    //You can replace "ok" with your defined "error code"
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find Room with id= " + id, "")
            );
        }
    }


    @PostMapping("/insertRoom")
    ResponseEntity<ResponseObject> insertRoom(@RequestBody Room newRoom) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Room Successfully", repository.save(newRoom))
        );
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> checkRoom(@RequestBody Room newRoom) { //Check if RoomID is duplicate or not
        List<Room> foundRoom = repository.findByid((newRoom.getId())
        );
        if (foundRoom.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Room name already taken", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Room Successfully", repository.save(newRoom))
        );
    }

    // PUT mapping for Room class
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateRoom(@RequestBody Room newRoom, @PathVariable Long id) {
        Room updatedRoom = repository.findById(id).map(room -> {
            room.setId(newRoom.getId());
            room.setDepartment(newRoom.getDepartment());
            room.setRoom(newRoom.getRoom());
            room.setStatus(newRoom.getStatus());
            return repository.save(room);
        }).orElseGet(() -> {
            newRoom.setId(id);
            return repository.save(newRoom);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Room Successfully", updatedRoom)
        );
    }

    //Delete a Room => DELETE method
    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteRoom(@PathVariable Long id) {
        boolean exists = repository.existsById(id);
        if(exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete Room successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find Room to DELETE", "")
        );
    }

}
