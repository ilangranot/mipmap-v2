package com.pleyfair.mipmip.model.dto.endato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pleyfair.mipmip.model.dto.request.Tmc4uPerson;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

// THIS IS THE REQUEST
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="request")
@Entity(name="Request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    List<Response> responses;

    public String FirstName; // = null (optional, string).. First Name
    String MiddleName; // = null (optional, string).. Middle name or middle initial
    public String LastName; // = null (optional, string)... Last name
    String Akas; //  = null (optional, Name{})....Alternative Names
    String Dob; //  = null (optional, string)..Date of birth (format: mm/dd/yyyy).
    String Age; //  = null (optional, int)...Age.
    String AgeRangeMinAge; //  = null (optional, int)...Age range minimum.
    String AgeRangeMaxAge; //  = null (optional, int)...Age Range Maximum
    String AgeRange; //  = null (optional, string)..Age Range (Format: ##-##).
    String Ssn; //  = null (optional, string)...Social security number (format: ###-##-####).

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    List<Addresses> Addresses;  // SEE ADDRESSES NESTED CLASS

    public String Email; //  = null (optional, string)..E-mail Address
    String ClientIp; //  = null (optional, int)... Search by IP Address (format: ###.###.###.###)
    public String Phone; //  = null (optional, string)...Phone number (formats: ###-###-####, (###)###-####).
    String Relatives; //  = null (optional, Name{})...List of names of relatives
    String TahoeIds; //  = null (optional, string{})...Tahoe ID is a unique person ID attached to each person record.
    String FirstNameCharOffset; //  = null (optional, int)... Uses levenshtein distance to filter out records based on the offset passed in. Applies to first name.
    String LastNameCharOffset; //  = null (optional, int)... Uses levenshtein distance to filter out records based on the offset passed in. Applies to last name.
    String DobFormat; //  = null (optional, string)... Overrides the default DOB date format returned. Supported date formats follow the mm-dd-yyyy etc.
    String MaxAddressYears; //  = null (optional, int)... Filters out addresses beyond the number of years passed in.
    String MaxPhoneYears; //  = null (optional, int)... Filters out phone numbers beyond the number of years passed in.

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tmc4u_person")
    private Tmc4uPerson tmc4uPerson;

}
