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
@Table(name="HighRiskMarker")
public class HighRiskMarker{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "address_id")
    public Address address;

    @Column(name = "is_high_risk")
    public boolean isHighRisk;

    @Column(name = "sic")
    public String sic;

    @Column(name = "address_type")
    public String addressType;
}