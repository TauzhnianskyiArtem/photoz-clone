package com.photoz.clone.service;

import com.photoz.clone.api.dto.PhotozFilter;
import com.photoz.clone.api.dto.PhotozReadDto;
import com.photoz.clone.api.mapper.PhotozReadMapper;
import com.photoz.clone.store.entity.Photo;
import com.photoz.clone.store.entity.User;
import com.photoz.clone.store.querydsl.QPredicates;
import com.photoz.clone.store.repository.PhotozRepository;
import com.photoz.clone.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

import static com.photoz.clone.store.entity.QPhoto.photo;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PhotozService {

    private final PhotozRepository photozRepository;
    private final PhotozReadMapper photozReadMapper;
    private final UserRepository userRepository;
    private final ImageService imageService;

    public Iterable<Photo> get() {
        return photozRepository.findAll();
    }

    public Optional<PhotozReadDto> get(Long id) {
        return photozRepository.findById(id).map(photozReadMapper::map);
    }

    @Transactional
    public void remove(Long id) {
        photozRepository.deleteById(id);
    }

    @Transactional
    public PhotozReadDto save(String fileName, String contentType, InputStream inputStream, String username) {
        imageService.upload(fileName, inputStream);
        User author = userRepository.findByUsername(username).orElseThrow();
        Photo photo = Photo.builder()
                .contentType(contentType)
                .fileName(fileName)
                .author(author)
                .build();
        Photo savedPhoto = photozRepository.save(photo);
        return photozReadMapper.map(savedPhoto);
    }

    public Page<PhotozReadDto> findAll(PhotozFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(filter.getFileName(), photo.fileName::containsIgnoreCase)
                .add(filter.getContentType(), photo.contentType::containsIgnoreCase)
                .build();

        return photozRepository.findAll(predicate, pageable)
                .map(photozReadMapper::map);
    }
}
