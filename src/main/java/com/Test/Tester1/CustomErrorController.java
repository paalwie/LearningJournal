package com.Test.Tester1;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public
    String error() {
        return "error";
        // Zeigt die zuvor erstellte error.html an
    }

    public String getErrorPath() {
        return PATH;
    }
}