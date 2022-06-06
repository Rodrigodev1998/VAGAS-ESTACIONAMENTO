package com.api.parkingstop.repositories;

import com.api.parkingstop.models.ParkingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingModel, UUID> {


    boolean existsByLicensePlateCar(String licensePlateCar);

    boolean existsByParkingNumber(String parkingNumber);

    boolean existsByApartamentAndBlock(String apartment);
}
