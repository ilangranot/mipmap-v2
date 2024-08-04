package com.pleyfair.mipmip.model.dto.endato;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name="Person")
@Table(name="person")
public class Person{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="response_id")
    private Response response;

    @Column(name = "tahoe_id")
    public String tahoeId;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    public Name name;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    public Learning learning;

    @Column(name = "is_public")
    public boolean isPublic;

    @Column(name = "is_main")
    public boolean isMain;

    @Column(name = "has_related")
    public boolean hasRelated;

    @Column(name = "total_related")
    public int totalRelated;

    @Column(name = "char_count")
    public int commonNameCharsCount;

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

    @Column(name = "dob_first_seen")
    public String dobFirstSeen;

    @Column(name = "dob_last_seen")
    public String dobLastSeen;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    public List<DatesOfDeath> datesOfDeath;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    public List<DatesOfBirth> datesOfBirth;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    public List<Aka> akas;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    public List<MergedName> mergedNames;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    public List<Location> locations;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    public List<Address> addresses;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    public List<PhoneNumber> phoneNumbers;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    public List<EmailAddress> emailAddresses;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    public List<RelativesSummary> relativesSummary;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    public List<AssociatesSummary> associatesSummary;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    public List<Associate> associates;

    @Column(name = "full_name")
    public String fullName;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    public Indicators indicators;

    @Column(name = "drivers_license_detail")
    public List<Object> driversLicenseDetail;

    @Column(name = "has_additional_data")
    public boolean hasAdditionalData;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    public PropensityToPayScore propensityToPayScore;
}