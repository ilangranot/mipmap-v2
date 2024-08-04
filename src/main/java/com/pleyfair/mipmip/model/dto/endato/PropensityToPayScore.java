package com.pleyfair.mipmip.model.dto.endato;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Slf4j
@Table(name="PropensityToPayScore")
public class PropensityToPayScore{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "person_uuid")
    Person person;

    @OneToOne
    @JoinColumn(name = "associate_id")
    Associate associate;

    public String transactionId;
    public String suffix;
    public String firstName;
    public String middleName;
    public String lastName;
    public String address;
    public String city;
    public String state;
    public String zip;
    public String zip4;
    public List<Object> measures;
}