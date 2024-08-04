package com.pleyfair.mipmip.model.dto.endato;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Table(name="DatesOfDeath")
public class DatesOfDeath{
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

    public String dod;

    @Transient
    public Object sourceSummary;
}
