package com.example.Bai01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class HomeController {

    // GET: Render the homepage
    @GetMapping("/")
    public String index() {
        return "index"; // Return index.html
    }

    // GET: Show contact form
    @GetMapping("/contact")
    public String showContactForm() {
        return "contact"; // Return contact.html
    }

    // POST: Process contact form submission
    @PostMapping("/contact")
    public String handleContactSubmission(@RequestParam Map<String, String> params, Model model) {
        model.addAllAttributes(params); // Pass submitted data to the view
        return "result"; // Return result.html
    }

    // GET: About page
    @GetMapping("/about")
    public String about() {
        return "about"; // Return about.html
    }

    // Handle unsupported HTTP methods or invalid URLs
    @ExceptionHandler(Exception.class)
    public String handleException() {
        return "error"; // Return a custom error page
    }
}
