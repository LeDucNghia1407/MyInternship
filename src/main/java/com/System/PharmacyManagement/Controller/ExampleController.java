package com.System.PharmacyManagement.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ExampleController {


    // This method handles the POST request sent from the form
    @PostMapping("/example/endpoint")
    public String handlePostRequest() {
        // Do something
        return "success"; // return the name of your success view
    }
}
