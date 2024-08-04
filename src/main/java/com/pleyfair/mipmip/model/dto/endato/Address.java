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
@Table(name="Address")
public class Address{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "associate_id")
    Associate associate;

    @ManyToOne
    @JoinColumn(name = "person_uuid")
    Person person;

    public boolean isDeliverable;
    public boolean isMergedAddress;
    public boolean isPublic;
    public List<String> addressQualityCodes;
    public String addressHash;
    public String houseNumber;
    public String streetPreDirection;
    public String streetName;
    public String streetPostDirection;
    public String streetType;
    public String unit;
    public String unitType;
    public String city;
    public String state;
    public String county;
    public String zip;
    public String zip4;
    public String latitude;
    public String longitude;
    public int addressOrder;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    public HighRiskMarker highRiskMarker;

    public String propertyIndicator;
    public String bldgCode;
    public String utilityCode;
    public int unitCount;
    public String firstReportedDate;
    public String lastReportedDate;
    public String publicFirstSeenDate;
    public String totalFirstSeenDate;
    public List<String> phoneNumbers;
    public List<Object> neighbors;
    public List<Object> neighborSummaryRecords;
    public String fullAddress;

    @Transient
    public Object sourceSummary;
}

