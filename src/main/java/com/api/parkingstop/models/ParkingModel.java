package com.api.parkingstop.models;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name="")
public class ParkingModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true, length= 10)
    private String parkingNumber;
    @Column(nullable = false, unique = true, length= 7)
    private String LicensePlateCar;
    @Column(nullable = false, length= 70)
    private String brandCar;
    @Column(nullable = false, length= 70)
    private String modelCar;
    @Column(nullable = false, length= 70)
    private String colorCar;
    @Column(nullable = false)
    private LocalDateTime registrationDate;
    @Column(nullable = false)
    private String responsibleName;
    @Column(nullable = false)
    private String apartment;
    @Column(nullable = false)
    private String block;
}
