package com.space.service;

import com.space.model.Ship;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public interface ShipService {

    List<Ship> getAll(Specification<Ship> specification, Pageable pageable);

    List<Ship> getAll(Specification<Ship> specification);

    void saveShip(Ship ship);

    Ship getOne(Long id);

    Ship update(Long id, Ship ship);

    void delete(Long id);
}
