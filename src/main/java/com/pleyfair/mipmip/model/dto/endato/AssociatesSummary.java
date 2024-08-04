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
@Table(name="AssociatesSummary")
public class AssociatesSummary{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "person_uuid")
    Person person;

    public String tahoeId;
    public String prefix;
    public String firstName;
    public String middleName;
    public String lastName;
    public String suffix;
    public boolean isPrivate;
    public boolean isOptedOut;
    public boolean isDeceased;
    public String dob;
    public int score;
}