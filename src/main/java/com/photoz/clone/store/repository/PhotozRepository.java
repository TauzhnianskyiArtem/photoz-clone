package com.photoz.clone.store.repository;

import com.photoz.clone.store.entity.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotozRepository extends CrudRepository<Photo, Integer> {
}
