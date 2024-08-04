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
@Table(name = "RelativesSummary")
public class RelativesSummary{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name="person_uuid")
    Person person;

    public String tahoeId;
    public String prefix;
    public String firstName;
    public String middleName;
    public String lastName;
    public String suffix;
    public String dob;
    public boolean isPrivate;
    public boolean isOptedOut;
    public boolean isDeceased;
    public String relativeLevel;
    public String relativeType;
    public int spouse;

    @Transient
    public List<String> sharedHouseholdIds;
    public int score;
    public boolean oldSpouse;
}