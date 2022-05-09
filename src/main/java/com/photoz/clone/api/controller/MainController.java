package com.photoz.clone.api.controller;

import com.photoz.clone.service.PhotozService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class MainController {

    private final PhotozService photozService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("photos", photozService.get());
        return "index";
    }

    @GetMapping("/upload")
    public String update(){return "upload";}

}
