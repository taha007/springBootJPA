package com.apress.prospring5.ch8.service.repository;

import com.apress.prospring5.ch8.entities.Singer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SingerRepository extends CrudRepository<Singer, Long> {
    List<Singer> findByFirstName(String firstName);

    List<Singer> findByFirstNameAndLastName(String firstName, String lastName);
}

