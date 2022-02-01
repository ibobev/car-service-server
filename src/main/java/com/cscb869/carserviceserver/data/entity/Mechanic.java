package com.cscb869.carserviceserver.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Mechanic {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    private Account account;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Qualification> qualifications = new HashSet<>();

}
