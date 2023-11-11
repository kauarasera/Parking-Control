package com.api.parkingcontrol.services;

import com.api.parkingcontrol.configs.ValidationException;
import com.api.parkingcontrol.controllers.ParkingSpotController;
import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ParkingSpotService {
    final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return parkingSpotRepository.save(parkingSpotModel);
    }

    @Transactional
    public ParkingSpotModel createParkingSpot(ParkingSpotDto parkingSpotDto) {
        if (existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
            throw new ValidationException("Conflict: License Plate Car is already in use!");
        }
        if (existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
            throw new ValidationException("Conflict: Parking Spot is already in use!");
        }
        if (existsByRoomAndFloor(parkingSpotDto.getRoom(), parkingSpotDto.getFloor())) {
            throw new ValidationException("Conflict: Parking Spot already registered for this room/floor!");
        }

        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setReservationDate(LocalDateTime.now(ZoneId.of("UTC")));

        return parkingSpotRepository.save(parkingSpotModel);
    }

    //   metodo customizado, será declarado dentro do repository antes de chamar ele aqui no Service
    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByRoomAndFloor(String room, String floor) {
        return parkingSpotRepository.existsByRoomAndFloor(room, floor);
    }

    public Page<ParkingSpotModel> findAll(Pageable pageable) {
        Page<ParkingSpotModel> parkingSpotModelList = parkingSpotRepository.findAll(pageable);

        if (!parkingSpotModelList.isEmpty()) {
            parkingSpotModelList.forEach(this::addHateoasLinks);
        }
        return parkingSpotModelList;
    }

    public Optional<ParkingSpotModel> findById(UUID id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotRepository.findById(id);

        if (parkingSpotModelOptional.isPresent()) {
            addHateoasLinks(parkingSpotModelOptional.get());
        }
        return parkingSpotModelOptional;
    }

    @Transactional //foi anotado aqui pois é um metodo destrutivo caso der algo errado eu tenho um rollback
    public void delete(ParkingSpotModel parkingSpotModel) {
        parkingSpotRepository.delete(parkingSpotModel);
    }

    public void addHateoasLinks(ParkingSpotModel parkingSpotModel) {
        UUID id = parkingSpotModel.getId();
        parkingSpotModel.add(linkTo(methodOn(ParkingSpotController.class).getOneParkingSpot(id)).withSelfRel());
    }
}
    /*para elevar o nivel de maturidade da aplicação posso criar uma interface para esse service InterfaceParkingSpotService
    e depois essa classe implementa essa interface, é interessante por que depois que precisarmos trocar essa implementação
    e cria uma outra classe que implementa essa mesma interface e todos o controllers que estavam utilizando não precisará
     de uma refatoração muito grande */