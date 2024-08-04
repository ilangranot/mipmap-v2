package com.pleyfair.mipmip.model.dto.endato;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Table(name="Indicators")
public class Indicators{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "person_uuid")
    Person person;

    public int hasBankruptcyRecords;
    public int hasBusinessRecords;
    public int hasDivorceRecords;
    public int hasDomainsRecords;
    public int hasEvictionsRecords;
    public int hasFeinRecords;
    public int hasForeclosuresRecords;
    public int hasForeclosuresV2Records;
    public int hasJudgmentRecords;
    public int hasLienRecords;
    public int hasMarriageRecords;
    public int hasProfessionalLicenseRecords;
    public int hasPropertyRecords;
    public int hasVehicleRegistrationsRecords;
    public int hasWorkplaceRecords;
    public int hasDeaRecords;
    public int hasPropertyV2Records;
    public int hasUccRecords;
    public int hasUnbankedData;
    public int hasMobilePhones;
    public int hasLandLines;
    public int hasEmails;
    public int hasAddresses;
    public int hasCurrentAddresses;
    public int hasHistoricalAddresses;
    public int hasDebtRecords;
}