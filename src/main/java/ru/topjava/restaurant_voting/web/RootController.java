package ru.topjava.restaurant_voting.web;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Hidden
@RequestMapping("/")
public class RootController {
    @GetMapping
    public String mainPage() {
        return "redirect:/swagger-ui/index.html";
    }
}
