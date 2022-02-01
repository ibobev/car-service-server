package com.cscb869.carserviceserver.data.entity;

import com.cscb869.carserviceserver.data.type.Category;
import com.cscb869.carserviceserver.data.type.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Account account;

    @OneToOne
    private CarServiceCompany carServiceCompany;

    @OneToOne
    private Car car;

    @Column(name = "date")
    private LocalDate date;

    @Column(name="start_time")
    private LocalTime startTime;

    @Column(name="end_time")
    private LocalTime endTime;

    @Column(name = "details")
    private String details;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name="cost")
    private double cost;

    @Column(name = "service_category")
    @Enumerated(EnumType.STRING)
    private Category category;

}
