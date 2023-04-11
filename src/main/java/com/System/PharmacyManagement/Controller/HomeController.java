package com.System.PharmacyManagement.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/api/v1/frontend")
public class HomeController {

    @PostMapping("/posting")
    public String po(@RequestBody String someName) {
        return "hello" + someName;
    }
    @GetMapping("/index") // go to localhost:8080/api/v1/frontend/index
    public String index() {
        return "index";
    }

    @GetMapping("/login") // go to localhost:8080/api/v1/frontend/login
    public String login()
    {
        return "login";
    }
    @GetMapping("/register") // go to localhost:8080/api/v1/frontend/register
    public String rigister()
    {
        return "register";
    }

    @GetMapping("/forum")
    public String home()
    {
        return "forum";
    }

    @GetMapping("/navigation")
    public String navigation()
    {
        return "navigation";
    }

    @GetMapping("/main")
    public String main() {

        return "main";
    }

}
