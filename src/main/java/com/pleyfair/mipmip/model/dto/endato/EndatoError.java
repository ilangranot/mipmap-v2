package com.pleyfair.mipmip.model.dto.endato;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Table(name="EndatoError")
public class EndatoError {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @SerializedName("requestId")
    String requestId;

    @SerializedName("requestType")
    String requestType;

    @SerializedName("requestTime")
    String requestTime;

    @SerializedName("isError")
    boolean isError;

    @SerializedName("error")
    Error error;

    @Getter
    public class Error {
        @SerializedName("code")
        String code;

        @SerializedName("message")
        String message;

        @SerializedName("technicalErrorMessage")
        String technicalErrorMessage;

        @SerializedName("inputErrors")
        List<String> inputErrors;

        @SerializedName("warnings")
        List<String> warnings;
    }
}
