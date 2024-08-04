package com.pleyfair.mipmip.model.dto.endato;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Slf4j
@Table(name="Location")
public class Location{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name="person_uuid")
    Person person;

    @ManyToOne
    @JoinColumn(name="associate_id")
    Associate associate;

    public String city;
    public String state;
}