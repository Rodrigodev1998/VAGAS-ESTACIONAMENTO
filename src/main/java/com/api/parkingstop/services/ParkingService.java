package com.api.parkingstop.services;

import com.api.parkingstop.models.ParkingModel;
import com.api.parkingstop.repositories.ParkingRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingService {


    final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }
    @Transactional
    public ParkingModel save(ParkingModel parkingModel) {
        return parkingRepository.save(parkingModel);
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingNumber(String ParkingNumber) {
        return parkingRepository.existsByParkingNumber(ParkingNumber);
    }

    public boolean existsByApartamentAndBlock(String apartment) {
        return parkingRepository.existsByApartamentAndBlock(apartment);
    }

    public List<ParkingModel> findAll() {
        return parkingRepository.findAll();
    }

    public Optional<ParkingModel> findById(UUID id) {
        return parkingRepository.findById(id);
    }
    @Transactional
    public void delete(ParkingModel parkingModel) {
        parkingRepository.delete(parkingModel);
    }
}
