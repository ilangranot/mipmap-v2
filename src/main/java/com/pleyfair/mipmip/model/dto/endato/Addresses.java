package com.pleyfair.mipmip.model.dto.endato;

import jakarta.persistence.*;
import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity(name="Addresses")
@Table(name="addresses")
public class Addresses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="request_id")
    private Request request;

    public String AddressLine1; // Addresses[].AddressLine1 = null (optional, string)..House number and street name or PO Box
    public String AddressLine2; //          Addresses[].AddressLine2 = null (optional, string)..State or City and State Or Zip
    public String County; //       Addresses[].County = null (optional, string)...County
}
