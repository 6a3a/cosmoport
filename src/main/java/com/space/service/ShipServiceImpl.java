package com.space.service;


import com.space.exception.BadRequestException;
import com.space.exception.ShipNotFoundException;
import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {

    private ShipRepository shipRepository;

    @Autowired
    public void setShipRepository(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @Override
    public List<Ship> getAll(Specification<Ship> specification, Pageable pageable) {
        return shipRepository.findAll(specification, pageable).getContent();
    }

    @Override
    public List<Ship> getAll(Specification<Ship> specification) {
        return shipRepository.findAll(specification);
    }

    @Override
    public void saveShip(Ship ship) {
        checkShipParameters(ship);
        ship.calculateAndSetRating();
        shipRepository.save(ship);
    }

    @Override
    public Ship getOne(Long id) {
        checkId(id);
        return shipRepository.findById(id).get();
    }


    @Override
    public Ship update(Long id, Ship ship) {
        checkId(id);
        Ship updateShip = shipRepository.findById(id).get();

        if (ship.getName() != null) {
            updateShip.setName(ship.getName());
        }
        if (ship.getPlanet() != null) {
            updateShip.setPlanet(ship.getPlanet());
        }
        if (ship.getShipType() != null) {
            updateShip.setShipType(ship.getShipType());
        }
        if (ship.getProdDate() != null) {
            updateShip.setProdDate(ship.getProdDate());
        }
        if (ship.getUsed() != null) {
            updateShip.setUsed(ship.getUsed());
        }
        if (ship.getSpeed() != null) {
            updateShip.setSpeed(ship.getSpeed());
        }
        if (ship.getCrewSize() != null) {
            updateShip.setCrewSize(ship.getCrewSize());
        }

        checkShipParameters(updateShip);
        updateShip.calculateAndSetRating();
        return shipRepository.save(updateShip);
    }

    @Override
    public void delete(Long id) {
        checkId(id);
        shipRepository.deleteById(id);
    }

    private void checkShipParameters(Ship ship) {
        checkName(ship.getName());
        checkPlanet(ship.getPlanet());
        checkProdDate(ship.getProdDate().getTime());
        checkSpeed(ship.getSpeed());
        checkCrewSize(ship.getCrewSize());
        checkUsage(ship);
    }

    private void checkName(String name) {
        if (name == null) {
            throw new BadRequestException("name null value");
        }
        int maxLength = 50;
        if (name.isEmpty() || name.length() > maxLength) {
            throw new BadRequestException("name length out of range");
        }
    }

    private void checkPlanet(String planet) {
        if (planet == null) {
            throw new BadRequestException("planet null value");
        }
        int maxLength = 50;
        if (planet.isEmpty() || planet.length() > maxLength) {
            throw new BadRequestException("planet length out of range");
        }
    }

    private void checkProdDate(Long prodDate) {
        if (prodDate == null) {
            throw new BadRequestException("prodDate null value");
        }

        int minYear = 2800;
        int maxYear = 3019;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(prodDate);
        int year = calendar.get(Calendar.YEAR);

        if (year < minYear || year > maxYear) {
            throw new BadRequestException("prodDate out of range");
        }
    }

    private void checkSpeed(Double speed) {
        if (speed == null) {
            throw new BadRequestException("speed null value");
        }
        double minSpeed = 0.01;
        double maxSpeed = 0.99;
        if (speed < minSpeed || speed > maxSpeed) {
            throw new BadRequestException("speed out of range");
        }
    }

    private void checkCrewSize(Integer crewSize) {
        if (crewSize == null) {
            throw new BadRequestException("crewSize null value");
        }
        int minCrewSize = 1;
        int maxCrewSize = 9999;

        if (crewSize < minCrewSize || crewSize > maxCrewSize) {
            throw new BadRequestException("crewSize out of range");
        }

    }

    private void checkUsage(Ship ship) {
        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }
    }


    private void checkId(Long id) {
        if (id == 0) {
            throw new BadRequestException("0 id");
        }
        if (!shipRepository.existsById(id)) {
            throw new ShipNotFoundException("Ship id not found: " + id);
        }
    }
}
