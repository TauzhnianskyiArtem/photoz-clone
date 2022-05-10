package com.photoz.clone.service;

import com.photoz.clone.store.entity.Photo;
import com.photoz.clone.store.entity.User;
import com.photoz.clone.store.repository.PhotozRepository;
import com.photoz.clone.store.repository.UserRepository;
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
    private final UserRepository userRepository;
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
    public Photo save(String fileName, String contentType, InputStream inputStream, String username) {
        imageService.upload(fileName, inputStream);
        User author = userRepository.findByUsername(username).orElseThrow();
        Photo photo = Photo.builder()
                .contentType(contentType)
                .fileName(fileName)
                .author(author)
                .build();
        photozRepository.save(photo);
        return photo;
    }
}
