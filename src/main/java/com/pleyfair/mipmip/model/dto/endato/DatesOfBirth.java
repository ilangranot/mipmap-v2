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
@Table(name="DatesOfBirth")
public class DatesOfBirth{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name="associate_id")
    Associate associate;

    @ManyToOne
    @JoinColumn(name="person_uuid")
    Person person;

    public int age;

    @Transient
    public Object sourceSummary;
}
