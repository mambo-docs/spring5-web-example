package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.TimeZone;

@Controller
public class HomeController {

    @GetMapping("/")
    public void home(HttpServletRequest request, HttpServletResponse response,
                     Model model, ModelMap modelMap,
                     HttpSession session, Locale locale, TimeZone timeZone) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println("Hello World");
    }

}
