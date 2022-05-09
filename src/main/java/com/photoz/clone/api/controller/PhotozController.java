package com.photoz.clone.api.controller;

import com.photoz.clone.store.entity.Photo;
import com.photoz.clone.service.PhotozService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public Photo create(@RequestPart("data") MultipartFile file) throws IOException {
        return photozService.save(file.getOriginalFilename(), file.getContentType(), file.getInputStream());
    }

    @GetMapping("{id}")
    public Photo get(@PathVariable Integer id) {
        return photozService.get(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        photozService.remove(id);
    }


}
