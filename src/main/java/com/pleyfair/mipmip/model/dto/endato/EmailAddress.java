package com.pleyfair.mipmip.model.dto.endato;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Table(name="EmailAddress")
public class EmailAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @OneToOne(mappedBy = "emailAddress", cascade = CascadeType.ALL)
    public EmailEngagementData emailEngagementData;

    @ManyToOne
    @JoinColumn(name = "associate_id")
    Associate associate;

    @ManyToOne
    @JoinColumn(name = "person_uuid")
    Person person;

    @Column(name = "email_address")
    public String emailAddress;

    @Column(name = "email_ordinal")
    public int emailOrdinal;

    @Column(name = "is_premium")
    public boolean isPremium;

    @Column(name = "no_business")
    public int nonBusiness;

    @Transient
    public Object sourceSummary;
}