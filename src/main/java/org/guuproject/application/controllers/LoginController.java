package org.guuproject.application.controllers;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(name="error",required = false) String error, HttpServletResponse response){
        response.addHeader("error", String.valueOf(error!=null));
        return "login";
    }

}
