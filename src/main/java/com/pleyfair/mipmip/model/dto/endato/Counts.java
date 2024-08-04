package com.pleyfair.mipmip.model.dto.endato;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Table(name="Counts")
public class Counts{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "response_id")
    Response response;

    @Column(name = "search_results")
    public int searchResults;

    @Column(name = "search_results_overflow")
    public boolean searchResultsOverflow;

    @Column(name = "names")
    public int names;

    @Column(name = "social_security_numbers")
    public int socialSecurityNumbers;

    @Column(name = "dates_of_birth")
    public int datesOfBirth;

    @Column(name = "dates_of_death")
    public int datesOfDeath;

    @Column(name = "death_records")
    public int deathRecords;

    @Column(name = "addresses")
    public int addresses;

    @Column(name = "phone_numbers")
    public int phoneNumbers;

    @Column(name = "email_addresses")
    public int emailAddresses;

    @Column(name = "relatives")
    public int relatives;

    @Column(name = "associates")
    public int associates;

    @Column(name = "business_records")
    public int businessRecords;

    @Column(name = "debt_records")
    public int debtRecords;

    @Column(name = "eviction_records")
    public int evictionRecords;

    @Column(name = "foreclosure_records")
    public int foreclosureRecords;

    @Column(name = "foreclosure_v2_records")
    public int foreclosureV2Records;

    @Column(name = "professional_license_records")
    public int professionalLicenseRecords;

    @Column(name = "expected_count")
    public int expectedCount;
}