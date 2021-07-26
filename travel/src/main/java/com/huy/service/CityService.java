package com.huy.service;

import com.huy.model.City;

import java.util.Optional;

public interface CityService {

    Iterable<City> findAll();

    Optional<City> findById(Long id);

    City save(City city);

    void deleteById(Long id);
}
