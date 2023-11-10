package com.api.parkingcontrol.services;

import com.api.parkingcontrol.configs.ValidationException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpotService {

    //injetando dependencia do ParkingSpotRepository
    final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return parkingSpotRepository.save(parkingSpotModel);
    }

//    //metodo customizado, será declarado dentro do repository antes de chamar ele aqui no Service
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
        return parkingSpotRepository.findAll(pageable);
    }

    public Optional<ParkingSpotModel> findById(UUID id) {
        return parkingSpotRepository.findById(id);
    }

    @Transactional //foi anotado aqui pois é um metodo destrutivo caso der algo errado eu tenho um rollback
    public void delete(ParkingSpotModel parkingSpotModel) {
        parkingSpotRepository.delete(parkingSpotModel);
    }
}
    /*para elevar o nivel de maturidade da aplicação posso criar uma interface para esse service InterfaceParkingSpotService
    e depois essa classe implementa essa interface, é interessante por que depois que precisarmos trocar essa implementação
    e cria uma outra classe que implementa essa mesma interface e todos o controllers que estavam utilizando não precisará
     de uma refatoração muito grande */