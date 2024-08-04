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
@Table(name="PhoneNumber")
public class PhoneNumber{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "person_uuid")
    Person person;

    @ManyToOne
    @JoinColumn(name = "associate_id")
    Associate associate;

    public String phoneNumber;
    public String company;
    public String location;
    public String phoneType;
    public boolean isConnected;
    public boolean isPublic;
    public String latitude;
    public String longitude;
    public int phoneOrder;
    public String firstReportedDate;
    public String lastReportedDate;
    public String publicFirstSeenDate;
    public String totalFirstSeenDate;

    @Transient
    public Object sourceSummary;
}