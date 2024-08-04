package com.pleyfair.mipmip.model.dto.process;

import com.pleyfair.mipmip.model.enums.Status;
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
@Table(name="Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "transaction_status")
    private Status status;

    @Column(name = "csv_file_id")
    private long csvFileId;

    @Column(name = "batch_id")
    private long batchId;

    @Column(name = "datetime_created")
    private String dateTimeCreated;

    @Column(name = "datetime_success")
    private String dateTimeSuccess;

    @Column(name = "datetime_last_attempt")
    private String dateTimeLastAttempt;

    @Column(name = "datetime_last_failure")
    private String dateTimeLastFailure;

    @Column(name = "successful_records")
    private int successfulRecords;

    @Column(name = "failed_records")
    private int failedRecords;
}
