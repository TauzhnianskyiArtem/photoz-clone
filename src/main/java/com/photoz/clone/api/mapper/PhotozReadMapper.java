package com.photoz.clone.api.mapper;

import com.photoz.clone.api.dto.PhotozReadDto;
import com.photoz.clone.api.dto.UserReadDto;
import com.photoz.clone.store.entity.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PhotozReadMapper implements Mapper<Photo, PhotozReadDto> {

    private final UserReadMapper userReadMapper;

    @Override
    public PhotozReadDto map(Photo object) {

        UserReadDto userReadDto = Optional.ofNullable(object.getAuthor())
                .map(userReadMapper::map)
                .orElse(null);

        return new PhotozReadDto(object.getId(), object.getFileName(), object.getContentType(), userReadDto);
    }
}
