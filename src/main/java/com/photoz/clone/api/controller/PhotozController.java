package com.photoz.clone.api.controller;

import com.photoz.clone.api.dto.PhotozReadDto;
import com.photoz.clone.service.PhotozService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/v1/api/photoz")
@RestController
public class PhotozController {

    private final PhotozService photozService;

    @PostMapping
    public PhotozReadDto create(@RequestPart("data") MultipartFile file, @AuthenticationPrincipal User user) throws IOException {
        return photozService.save(file.getOriginalFilename(), file.getContentType(), file.getInputStream(), user.getUsername());
    }

    @GetMapping("{id}")
    public PhotozReadDto get(@PathVariable Long id) {
        return photozService.get(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        photozService.remove(id);
    }


}
