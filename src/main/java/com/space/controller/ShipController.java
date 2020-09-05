package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class ShipController {

    @Autowired
    private ShipService shipService;
    @Autowired
    private ShipSpecification shipSpecification;

    @GetMapping("/ships")
    public List<Ship> getShipsList(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "planet", required = false) String planet,
                                   @RequestParam(value = "shipType", required = false) ShipType shipType,
                                   @RequestParam(value = "after", required = false) Long after,
                                   @RequestParam(value = "before", required = false) Long before,
                                   @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                   @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                   @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                   @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                   @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                   @RequestParam(value = "minRating", required = false) Double minRating,
                                   @RequestParam(value = "maxRating", required = false) Double maxRating,
                                   @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order,
                                   @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {

        Specification<Ship> specification = shipSpecification.getSpecification(name, planet, shipType, after,
                before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating);

        Sort sort = Sort.by(order.getFieldName());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return shipService.getAll(specification, pageable);
    }

    @GetMapping("/ships/count")
    public Integer getShipsCount(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "planet", required = false) String planet,
                                 @RequestParam(value = "shipType", required = false) ShipType shipType,
                                 @RequestParam(value = "after", required = false) Long after,
                                 @RequestParam(value = "before", required = false) Long before,
                                 @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                 @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                 @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                 @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                 @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                 @RequestParam(value = "minRating", required = false) Double minRating,
                                 @RequestParam(value = "maxRating", required = false) Double maxRating) {

        Specification<Ship> specification = shipSpecification.getSpecification(name, planet, shipType, after,
                before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating);

        return shipService.getAll(specification).size();
    }

    @PostMapping("/ships")
    public Ship createShip(@RequestBody Ship ship) {
        shipService.saveShip(ship);
        return ship;
    }

    @GetMapping("/ships/{id}")
    public Ship getShip(@PathVariable Long id) {
        return shipService.getOne(id);
    }

    @PostMapping("/ships/{id}")
    public Ship updateShip(@PathVariable Long id, @RequestBody Ship ship) {
        return shipService.update(id, ship);
    }

    @DeleteMapping("/ships/{id}")
    public void deleteShip(@PathVariable Long id) {
        shipService.delete(id);
    }
}
