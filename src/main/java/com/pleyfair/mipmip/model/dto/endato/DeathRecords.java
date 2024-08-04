package com.pleyfair.mipmip.model.dto.endato;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Table(name="DeathRecords")
public class DeathRecords{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "associate_id")
    private long associateId;

    @OneToOne
    @JoinColumn(name = "associate_id")
    public Associate associate;

    @Column(name = "is_high_risk")
    public boolean isDeceased;

    @Transient
    public Object sourceSummary;
}