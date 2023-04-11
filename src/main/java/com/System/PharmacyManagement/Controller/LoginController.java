package com.System.PharmacyManagement.Controller;

import com.System.PharmacyManagement.Model.Login;
import com.System.PharmacyManagement.Model.ResponseObject;
import com.System.PharmacyManagement.Repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController // Gọi java springboot
@RequestMapping("/api/v1/backend/Login") // Địa chỉnh chính (vô nhánh localhost:8080/api/v1/backend/Login )
public class LoginController {

    @Autowired
    private LoginRepository repository;

    @GetMapping("get") // (vô nhánh localhost:8080/api/v1/backend/Login/get )
    List<Login> getAllLogins() {
        List<Login> logins = repository.findAll();
        for (Login l: logins)
        {
            System.out.println("Id: "+ l.getId() + " Username: "+ l.getUsername() + " Password: " + l.getPassword());
        }

        return logins;
    }



    @PostMapping("/login") // (vô nhánh localhost:8080/api/v1/backend/Login/login )
    @ResponseBody
    String login(@RequestParam String username, @RequestParam String password) {
        List<Login> logins = repository.findAll();

        for (Login l: logins)
        {
            if (l.getUsername().equals(username) && l.getPassword().equals(password)) {
                System.out.println("Username: " + l.getUsername() + " Password: " + l.getPassword());
                return "success";
            }
        }

        return "failure";
    }




    @PostMapping("/register") // (vô nhánh localhost:8080/api/v1/backend/Login/register )
    public String registerPost(@RequestParam String username, @RequestParam String password, ModelMap modelMap) {
        Login login = new Login();
        login.setUsername(username);
        login.setPassword(password);
        System.out.println(login.toString());
        repository.save(login);

        modelMap.addAttribute("username", username);
        return "login";
    }





    @GetMapping("get/{id}") // (vô nhánh localhost:8080/api/v1/backend/Login/get/1 )
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Login> foundLogin = repository.findById(id);
        if (foundLogin.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query Login successfully", foundLogin)
                    //You can replace "ok" with your defined "error code"
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find Login with id= " + id, "")
            );
        }
    }


    @PostMapping("/insertLogin") // (vô nhánh localhost:8080/api/v1/backend/Login/insertLogin )
    ResponseEntity<ResponseObject> insertLogin(@RequestBody Login newLogin) {
        Login savedLogin = repository.save(newLogin);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "User registered successfully", savedLogin)
        );
    }


    @PostMapping("/insert") // (vô nhánh localhost:8080/api/v1/backend/Login/insert )
    ResponseEntity<ResponseObject> checkLogin(@RequestBody Login newLogin) { //Check if LoginID is duplicate or not
        List<Login> foundLogin = repository.findByid((newLogin.getId())
        );
        if (foundLogin.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Login name already taken", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Login Successfully", repository.save(newLogin))
        );
    }




    @PostMapping("/insertSpecial") // (vô nhánh localhost:8080/api/v1/backend/Login/insertSpecial )
    public Login createSpecialLogin(@RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        String password = (String) request.get("password");
        System.out.println("username: " + username + " password: " + password);
        Login login = new Login();
        if (username != null) {
            login.setUsername(username);
        }
        if (password != null) {
            login.setPassword(password);
        }
        repository.save(login);
        return login;
    }

    @PostMapping("/insertMultiple") // (vô nhánh localhost:8080/api/v1/backend/Login/insertMultiple )
    public ResponseEntity<ResponseObject> insertMultipleLogins(@RequestBody List<Login> newLogins) {
        List<Login> insertedLogins = new ArrayList<>();
        List<String> failedLogins = new ArrayList<>();
        for (Login login : newLogins) {
            if (login.getUsername() == null || login.getPassword() == null) {
                failedLogins.add("Missing required attribute(s) for Login with ID " + login.getId() + ".");
                continue;
            }
            List<Login> foundLogins = repository.findByid(login.getId());
            if (foundLogins.size() > 0) {
                failedLogins.add("Duplicate ID: " + login.getId());
            } else {
                insertedLogins.add(repository.save(login));
            }
        }
        String message = "";
        if (insertedLogins.size() > 0) {
            message += "Inserted " + insertedLogins.size() + " Login(s) successfully.";
        }
        if (failedLogins.size() > 0) {
            message += " Failed to insert " + failedLogins.size() + " Login(s): " + String.join(", ", failedLogins);
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", message, "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", message, insertedLogins)
        );
    }


    // PUT mapping for Login class
    @PutMapping("/{id}") // (vô nhánh localhost:8080/api/v1/backend/Login/1 )
    ResponseEntity<ResponseObject> updateLogin(@RequestBody Login newLogin, @PathVariable Long id) {
        Login updatedLogin = repository.findById(id).map(login -> {
            login.setId(newLogin.getId());
            login.setUsername(newLogin.getUsername());
            login.setPassword(newLogin.getPassword());
            return repository.save(login);
        }).orElseGet(() -> {
            newLogin.setId(id);
            return repository.save(newLogin);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Login Successfully", updatedLogin)
        );
    }

    //Delete a Login => DELETE method
    @DeleteMapping("/delete/{id}") // (vô nhánh localhost:8080/api/v1/backend/Login/delete/1 )
    ResponseEntity<ResponseObject> deleteLogin(@PathVariable Long id) {
        boolean exists = repository.existsById(id);
        if(exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete Login successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find Login to DELETE", "")
        );
    }

}
