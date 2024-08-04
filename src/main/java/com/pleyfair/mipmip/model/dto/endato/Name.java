package com.pleyfair.mipmip.model.dto.endato;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Table(name="Name")
public class Name {
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


    @Column(name = "prefix")
    public String prefix;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "middle_name")
    public String middleName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "suffix")
    public String suffix;

    @Column(name = "raw_names")
    public ArrayList<String> rawNames;

    @Column(name = "public_first_seen_date")
    public String publicFirstSeenDate;

    @Column(name = "total_first_seen_date")
    public String totalFirstSeenDate;

    @Transient
    public Object sourceSummary;
}