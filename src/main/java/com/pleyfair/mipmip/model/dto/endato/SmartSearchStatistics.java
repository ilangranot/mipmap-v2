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
@Table(name="SmartSearchStatistics")
public class SmartSearchStatistics{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "response_id")
    Response response;

    @Transient
    public Object userInput;

    @Transient
    public Object criteriaGroupId;
    public boolean isSuccessful;

    @Transient
    public Object successfulPattern;

    public int totalTimeInMS;
    public int resultCount;

    @Transient
    public List<Object> patterns;

    @Transient
    public List<Object> searches;
}