package com.cscb869.carserviceserver.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CarServiceCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name="city")
    private String city;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name ="created_at")
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;

    @OneToMany
    private Set<Mechanic> mechanics = new HashSet<>();

    @ManyToMany
    private Set<Account> clients = new HashSet<>();
}
