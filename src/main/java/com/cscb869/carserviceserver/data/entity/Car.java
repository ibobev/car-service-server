package com.cscb869.carserviceserver.data.entity;

import lombok.*;
import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="plate_number", unique = true)
    private String plateNumber;

    @Column(name="brand")
    private String brand;

    @Column(name="model")
    private String model;

    @Column(name="year")
    private String year;

    /*@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name="client_id")
    private Account account;*/
    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;
}
