package com.pleyfair.mipmip.model.dto.endato;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

// TODO: get a json response with errors to complete this schema
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Table(name="Error")
public class Error{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "response_id")
    Response response;

    public List<Object> inputErrors;
    public List<String> warnings;
}
