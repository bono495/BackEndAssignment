package com.bonoarts.assignment.controller;

import com.bonoarts.assignment.model.Info;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/info")
public class InfoController {

    @GetMapping(value = "", produces = { "application/json" })
    public String info() {
        return Info.getInfo();
    }

}
