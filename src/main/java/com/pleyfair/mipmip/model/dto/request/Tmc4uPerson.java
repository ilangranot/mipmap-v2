package com.pleyfair.mipmip.model.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="Tmc4uPerson")
public class Tmc4uPerson {
    // fields copied from the CSV sent to me
    // Lead ID,Date,Campaign,Lead price,name,email,I am moving from,I am moving to,Address,Postcode / ZIP,City,Origin region,Destination City,When are you moving?,What would you like to move?,Describe your move,Telephone ,Contact by,Best day to call,Best time to call,Who is paying for the move?,Company name,Do you want moving insurance?,Estimated insurance value of your household goods?,"Do you need help with your utilities (electricity, gas, internet)?",Are you interested in our departure services?,Are you interested in our settling-in services?,Can we coordinate your pick-up at the airport?,Do you need help with your expense management?,Do you need school search assistance for your kids?,Add photos

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @JsonIgnore
    @Column(name = "batch_id")
    private long batchId;

    @JsonIgnore
    @Column(name =  "isProcessed")
    private String isProcessed;

    @JsonAlias(value = "token")
    private String token;

    @JsonAlias(value = "lead_id")
    private String leadId;

    @JsonAlias(value = "first_name")
    private String firstName;

    @JsonAlias(value = "last_name")
    private String lastName;

    @JsonAlias(value = "phone")
    private String phone;

    @JsonAlias(value = "mobile")
    private String mobile;

    @JsonAlias(value = "email")
    private String email;

    @JsonAlias(value = "from_address")
    private String fromAddress;

    @JsonAlias(value = "from_zip")
    private String fromZip;

    @JsonAlias(value = "to_address")
    private String toAddress;

    @JsonAlias(value = "to_zip")
    private String toZip;

    @JsonAlias(value = "moving_weight")
    private String movingWeight;

    @JsonAlias(value = "type")
    private String type;

    @JsonAlias(value = "moving_date")
    private String movingDate;

    @JsonAlias(value = "additional_information")
    private String additionalInformation;
}