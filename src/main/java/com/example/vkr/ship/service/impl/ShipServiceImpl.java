package com.example.vkr.ship.service.impl;

import com.example.vkr.base.repository.BaseRepository;
import com.example.vkr.base.service.impl.BaseServiceImpl;
import com.example.vkr.exception.EntityExistsException;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.*;
import com.example.vkr.ship.repository.ShipRepository;
import com.example.vkr.ship.service.ShipService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service("shipService")
public class ShipServiceImpl extends BaseServiceImpl<Ship, Integer> implements ShipService {

    @Resource(name = "shipRepository")
    ShipRepository shipRepository;

    /**
     * Creates {@link ShipServiceImpl} for the given {@link BaseRepository}
     * @param baseRepository must not be {@literal null}.
     */
    public ShipServiceImpl(BaseRepository<Ship, Integer> baseRepository) {
        super(baseRepository);
    }

    @Override
    public <S extends Ship> S save(S ship) throws EntityExistsException {
        if (ship.getShipCapacity() == null) {
            ship.setShipCapacity(new ShipCapacity(ship));
        }
        if (ship.getShipDimensions() == null) {
            ship.setShipDimensions(new ShipDimensions(ship));
        }
        if (ship.getShipEngine() == null) {
            ship.setShipEngine(new ShipEngine(ship));
        }
        return super.save(ship);
    }

    @Override
    public List<Ship> findAllWithSort(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sort));
        return findAll(pageable);
    }

    //TODO
    @Override
    public ShipDto findByIdWithJoin(int id, boolean isCapacity, boolean isDimensions, boolean isEngines) throws EntityNotFoundException {

        Optional<Ship> optionalShip = shipRepository.findById(id);

        if (optionalShip.isEmpty()) {
            throw new EntityNotFoundException(String.format("No ship entity with id %d", id));
        }
        Ship ship = optionalShip.get();
        ShipDto shipDto = new ShipDto(ship);
        if (isCapacity) {
            shipDto.setShipCapacity(ship.getShipCapacity());
        }
        if (isDimensions) {
            shipDto.setShipDimensions(ship.getShipDimensions());
        }
        if (isEngines) {
            shipDto.setShipEngine(ship.getShipEngine());
        }
        return shipDto;
    }
}