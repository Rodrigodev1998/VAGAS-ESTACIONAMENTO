package com.api.parkingstop.controllers;

import com.api.parkingstop.ParkingDto;
import com.api.parkingstop.models.ParkingModel;
import com.api.parkingstop.services.ParkingService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking")
public class ParkingController {

    final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping
    public ResponseEntity<Object> saveParking(@RequestBody @Valid ParkingDto parkingDto){
        if (parkingService.existsByLicensePlateCar(parkingDto.getLicensePlateCar())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("conflict: Já existe Registro para esta placa");
        }
        if (parkingService.existsByParkingNumber(parkingDto.getParkingNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("conflict: Já existe Registro no sistema");
        }
        if (parkingService.existsByApartamentAndBlock(parkingDto.getApartment())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("conflict: Já existe Registro para esse apartamento/bloco");
        }
        var parkingModel = new ParkingModel();
        BeanUtils.copyProperties(parkingDto, parkingModel);
        parkingModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingService.save(parkingModel));
    }

    @GetMapping
    public ResponseEntity<List<ParkingModel>> getAllParkings(){
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParking(@PathVariable(value = "id") UUID id){
        Optional<ParkingModel> parkingModelOptional = parkingService.findById(id);
        if (!parkingModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe vaga.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParking(@PathVariable(value = "id") UUID id){
        Optional<ParkingModel> parkingModelOptional = parkingService.findById(id);
        if (!parkingModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe vaga.");
        }
        parkingService.delete(parkingModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Vaga deletada com Sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParking(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ParkingDto parkingDto){
        Optional<ParkingModel> parkingModelOptional = parkingService.findById(id);
        if (!parkingModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe vaga.");
        }
        var parkingModel = new ParkingModel();
        BeanUtils.copyProperties(parkingDto, parkingModel);
        parkingModel.setId(parkingModelOptional.get().getId());
        parkingModel.setRegistrationDate(parkingModelOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.save(parkingModel));
    }

}
