package com.photoz.clone.api.controller;

import com.photoz.clone.api.dto.PageResponse;
import com.photoz.clone.api.dto.PhotozFilter;
import com.photoz.clone.api.dto.PhotozReadDto;
import com.photoz.clone.service.PhotozService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public String index(Model model, PhotozFilter filter, Pageable pageable) {
        Page<PhotozReadDto> page = photozService.findAll(filter, pageable);
        model.addAttribute("photos", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "index";
    }

    @GetMapping("/upload")
    public String update(){return "upload";}

}
