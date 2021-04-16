package com.worldtravel.demo.repository;

import com.worldtravel.demo.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Country findByName(String CountryName);
    Optional<Country> findById(Long id);
}
