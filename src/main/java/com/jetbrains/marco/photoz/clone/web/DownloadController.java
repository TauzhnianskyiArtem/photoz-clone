package com.jetbrains.marco.photoz.clone.web;

import com.jetbrains.marco.photoz.clone.model.Photo;
import com.jetbrains.marco.photoz.clone.service.ImageService;
import com.jetbrains.marco.photoz.clone.service.PhotozService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;

@RequiredArgsConstructor
@RestController
public class DownloadController {

    private final PhotozService photozService;
    private final ImageService imageService;


    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Integer id) {
        Photo photo = photozService.get(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ContentDisposition build = ContentDisposition
                .builder("attachment")
                .filename(photo.getFileName())
                .build();

        return imageService.get(photo.getFileName())
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.valueOf(photo.getContentType())))
                        .header(HttpHeaders.CONTENT_DISPOSITION, String.valueOf(build))
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }
}
