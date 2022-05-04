package com.jetbrains.marco.photoz.clone.web;

import com.jetbrains.marco.photoz.clone.model.Photo;
import com.jetbrains.marco.photoz.clone.service.PhotozService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
@RestController
public class PhotozController {

    private final PhotozService photozService;

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/photoz")
    public Iterable<Photo> get() {
        return photozService.get();
    }

    @GetMapping("/photoz/{id}")
    public Photo get(@PathVariable Integer id) {
        return photozService.get(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/photoz/{id}")
    public void delete(@PathVariable Integer id) {
        photozService.remove(id);
    }

    @PostMapping("/photoz")
    public Photo create(@RequestPart("data") MultipartFile file) throws IOException {
        return photozService.save(file.getOriginalFilename(), file.getContentType(), file.getInputStream());
    }
}
