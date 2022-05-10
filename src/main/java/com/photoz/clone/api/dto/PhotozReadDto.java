package com.photoz.clone.api.dto;

import lombok.Value;

@Value
public class PhotozReadDto {

    Long id;

    String fileName;

    String contentType;

    UserReadDto author;
}

