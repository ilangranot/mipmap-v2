package com.pleyfair.mipmip.model.dto.endato;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Learning")
public class Learning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "person_uuid")
    public Person person;

    @OneToOne
    @JoinColumn(name = "associate_id")
    public Associate associate;

    @Column(name = "age")
    public int age;

    @Column(name = "addressesCount")
    public int addressesCount;

    @Column(name = "firstSeenLocalDate")
    String firstSeenLocalDate;

    @Column(name = "lastSeenLocalDate")
    String lastSeenLocalDate;

    @Column(name = "stability")
    float stability;

    @Column(name = "addressesPrettyPrint", columnDefinition="text")
    String addressesPrettyPrint;

    @Column(name = "indicatorsPrettyPrint", columnDefinition="text")
    String indicatorsPrettyPrint;

    @Column(name = "hasNegativeIndicators")
    boolean hasNegativeIndicators;
}