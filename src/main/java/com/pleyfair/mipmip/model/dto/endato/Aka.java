package com.pleyfair.mipmip.model.dto.endato;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Table(name="Aka")
public class Aka{
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

    public String prefix;
    public String firstName;
    public String middleName;
    public String lastName;
    public String suffix;
    public List<String> rawNames;
    public String publicFirstSeenDate;
    public String totalFirstSeenDate;

    @Transient
    public Object sourceSummary;
}