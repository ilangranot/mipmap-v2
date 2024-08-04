package com.pleyfair.mipmip.model.dto.endato;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

// TODO: ONCE WORKING CHECK THAT EACH IS BI-DRIECTIONAL AND CORRECT

// Endato Response JSON
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Slf4j
@Entity(name="Response")
@Table(name="response")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="request_id")
    private Request request;

    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL)
    private List<Person> persons;

    @OneToOne(mappedBy = "response", cascade = CascadeType.ALL)
    public Counts counts;

    @OneToOne(mappedBy = "response", cascade = CascadeType.ALL)
    public SmartSearchStatistics smartSearchStatistics;

    @OneToOne(mappedBy = "response", cascade = CascadeType.ALL)
    public Pagination pagination;

    @Column(name = "search_criteria")
    public List<Object> searchCriteria;

    @Column(name = "total_request_execution_time_ms")
    public int totalRequestExecutionTimeMs;

    @Column(name = "request_type")
    public String requestType;

    @Column(name = "request_time")
    public Date requestTime;

    @Column(name = "is_error")
    public boolean isError;

    @Column(name = "is_main_found")
    public boolean isMainFound;

    @OneToOne(mappedBy = "response", cascade = CascadeType.ALL)
    public Error error;
}
