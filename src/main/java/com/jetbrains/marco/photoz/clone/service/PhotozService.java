package com.jetbrains.marco.photoz.clone.service;

import com.jetbrains.marco.photoz.clone.model.Photo;
import com.jetbrains.marco.photoz.clone.repository.PhotozRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PhotozService {

    private final PhotozRepository photozRepository;
    private final ImageService imageService;

    public Iterable<Photo> get() {
        return photozRepository.findAll();
    }

    public Optional<Photo> get(Integer id) {
        return photozRepository.findById(id);
    }

    @Transactional
    public void remove(Integer id) {
        photozRepository.deleteById(id);
    }

    @Transactional
    public Photo save(String fileName, String contentType, InputStream inputStream) {
        imageService.upload(fileName, inputStream);

        Photo photo = Photo.builder()
                .contentType(contentType)
                .fileName(fileName)
                .build();
        photozRepository.save(photo);
        return photo;
    }
}
