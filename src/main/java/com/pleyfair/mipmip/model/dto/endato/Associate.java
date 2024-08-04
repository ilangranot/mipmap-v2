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
@Table(name="Associate")
public class Associate{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name="person_uuid")
    public Person person;

    @OneToOne(mappedBy = "associate", cascade = CascadeType.ALL)
    public Name name;

    @OneToOne(mappedBy = "associate", cascade = CascadeType.ALL)
    public Learning learning;

    @Column(name = "tahoe_id")
    public String tahoeId;

    @Column(name = "is_public")
    public boolean isPublic;

    @Column(name = "is_opted_out")
    public boolean isOptedOut;

    @Column(name = "sparse_flag")
    public int sparseFlag;

    @Column(name = "is_premium")
    public boolean isPremium;

    @Column(name = "dob")
    public String dob;

    @Column(name = "age")
    public int age;

    @OneToMany(mappedBy = "associate", cascade = CascadeType.ALL)
    public List<DatesOfBirth> datesOfBirth;

    @OneToMany(mappedBy = "associate", cascade = CascadeType.ALL)
    public List<DatesOfDeath> datesOfDeath;

    @OneToOne(mappedBy = "associate", cascade = CascadeType.ALL)
    public DeathRecords deathRecords;

    @OneToMany(mappedBy = "associate", cascade = CascadeType.ALL)
    public List<Aka> akas;

    @OneToMany(mappedBy = "associate", cascade = CascadeType.ALL)
    public List<MergedName> mergedNames;

    @OneToMany(mappedBy = "associate", cascade = CascadeType.ALL)
    public List<Location> locations;

    @OneToMany(mappedBy = "associate", cascade = CascadeType.ALL)
    public List<Address> addresses;

    @OneToMany(mappedBy = "associate", cascade = CascadeType.ALL)
    public List<PhoneNumber> phoneNumbers;

    @OneToMany(mappedBy = "associate", cascade = CascadeType.ALL)
    public List<EmailAddress> emailAddresses;

    @Column(name = "full_name")
    public String fullName;

    @Column(name = "drivers_license_detail")
    public List<Object> driversLicenseDetail;

    @Column(name = "has_additional_data")
    public boolean hasAdditionalData;

    @OneToOne(mappedBy = "associate", cascade = CascadeType.ALL)
    public PropensityToPayScore propensityToPayScore;

    @Column(name = "dod")
    public String dod;
}