package com.System.PharmacyManagement.Controller;

import com.System.PharmacyManagement.Model.Login;
import com.System.PharmacyManagement.Repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class NameController {

    @Autowired
    LoginRepository repository;


    private boolean authenticateUser(String username, String password) {
        // Perform username and password validation here
        // For example, check if username and password match a pre-defined list
        return username.equals("exampleUser") && password.equals("examplePassword");
    }
    @RestController
    public class MyController {
        @PostMapping("/my-controller-mapping")
        public String handleButtonPress( @RequestParam String username,  @RequestParam String password, HttpSession session, Model model) {
            List<Login> logins = repository.findAll();

            for (Login l: logins) {
                if (l.getUsername().equals(username)) {
                    System.out.println("Already has username");
                    return "failure";
                }
            }

            System.out.println("username: " + username);
            System.out.println("password: " + password);

            Login login = new Login();
            login.setUsername(username);
            login.setPassword(password);
            repository.save(login);

            session.setAttribute("username", username);
            session.setAttribute("password", password);

            model.addAttribute("username", username);
            model.addAttribute("password", password);

            return "success";
        }
    }

    @GetMapping("/get-username-and-password")
    @ResponseBody
    public Map<String, String> getUsernameAndPassword(HttpSession session) {
        System.out.println("get-username-and-password");
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        System.out.println("Main page: username: " + username + " password: " + password);
        Map<String, String> response = new HashMap<>();
        response.put("username", username);
        response.put("password", password);

        return response;
    }

    @PostMapping("/check-username")
    @ResponseBody
    public Map<String, Object> checkUsername(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");



        List<Login> logins = repository.findAll();
        for (Login l: logins) {
            if (l.getUsername().equals(username)) {
                Map<String, Object> response = new HashMap<>();
                response.put("exists", true);
                return response;
            }
        }
        System.out.println(username);
        Map<String, Object> response = new HashMap<>();
        response.put("exists", false);
        return response;
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {
        // Authenticate user and set session attributes
        boolean isAuthenticated = authenticateUser(username, password);
        if (isAuthenticated) {
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            return "redirect:/main.html";
        } else {
            return "redirect:/login.html?error=InvalidCredentials";
        }
    }


}

