package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ShipSpecification {

    public Specification<Ship> filterByName(String name) {
        return (root, query, builder) -> name == null ? null : builder.like(root.get("name"), "%" + name + "%");
    }

    public Specification<Ship> filterByPlanet(String planet) {
        return (root, query, builder) -> planet == null ? null : builder.like(root.get("planet"), "%" + planet + "%");
    }

    public Specification<Ship> filterByProdYear(Long after, Long before) {
        if (after == null && before == null) {
            return null;
        }

        if (before == null) {
            return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("prodDate"), new Date(after));
        }

        if (after == null) {
            return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("prodDate"), new Date(before));
        }

        return (root, query, builder) -> builder.between(root.get("prodDate"), new Date(after), new Date(before));
    }

    public Specification<Ship> filterByCrewSize(Integer minCrewSize, Integer maxCrewSize) {
        if (minCrewSize == null && maxCrewSize == null) {
            return null;
        }

        if (maxCrewSize == null) {
            return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("crewSize"), minCrewSize);
        }

        if (minCrewSize == null) {
            return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("crewSize"), maxCrewSize);
        }

        return (root, query, builder) -> builder.between(root.get("crewSize"), minCrewSize, maxCrewSize);
    }

    public Specification<Ship> filterBySpeed(Double minSpeed, Double maxSpeed) {
        if (minSpeed == null && maxSpeed == null) {
            return null;
        }

        if (maxSpeed == null) {
            return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("speed"), minSpeed);
        }

        if (minSpeed == null) {
            return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("speed"), maxSpeed);
        }

        return (root, query, builder) -> builder.between(root.get("speed"), minSpeed, maxSpeed);
    }

    public Specification<Ship> filterByRating(Double minRating, Double maxRating) {
        if (minRating == null && maxRating == null) {
            return null;
        }

        if (maxRating == null) {
            return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("rating"), minRating);
        }

        if (minRating == null) {
            return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("rating"), maxRating);
        }

        return (root, query, builder) -> builder.between(root.get("rating"), minRating, maxRating);
    }

    public Specification<Ship> filterByShipType(ShipType shipType) {
        return (root, query, builder) -> shipType == null ? null : builder.equal(root.get("shipType"), shipType);
    }

    public Specification<Ship> filterByUsage(Boolean isUsed) {
        return (root, query, builder) -> isUsed == null ? null : builder.equal(root.get("isUsed"), isUsed);
    }

    public Specification<Ship> getSpecification(String name, String planet, ShipType shipType, Long after, Long before,
                                                Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize,
                                                Double minRating, Double maxRating) {
        return filterByName(name)
                .and(filterByPlanet(planet))
                .and(filterByShipType(shipType))
                .and(filterByProdYear(after, before))
                .and(filterByUsage(isUsed))
                .and(filterBySpeed(minSpeed, maxSpeed))
                .and(filterByCrewSize(minCrewSize, maxCrewSize))
                .and(filterByRating(minRating, maxRating));
    }
}
