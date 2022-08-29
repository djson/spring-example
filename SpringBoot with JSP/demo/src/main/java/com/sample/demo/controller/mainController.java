package com.sample.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class mainController {
    @GetMapping(value = "/index.do")
    public ModelAndView main() {
        return new ModelAndView("/index.main");
    }
}
