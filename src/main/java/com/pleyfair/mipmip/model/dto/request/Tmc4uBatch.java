package com.pleyfair.mipmip.model.dto.request;

import com.pleyfair.mipmip.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Table(name="Tmc4uBatch")
public class Tmc4uBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "file_process_status")
    private Status status;

    @Column(name = "datetime_uploaded")
    private String dateTime;

    @Column(name = "uploaded_by")
    private String uploadedBy;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "person_count")
    private int personCount;
}
