package com.pleyfair.mipmip.service;

import com.pleyfair.mipmip.exception.EmptyListException;
import com.pleyfair.mipmip.model.dto.process.Transaction;
import com.pleyfair.mipmip.model.dto.request.Tmc4uBatch;
import com.pleyfair.mipmip.model.dto.request.Tmc4uPerson;
import com.pleyfair.mipmip.model.enums.Status;
import com.pleyfair.mipmip.repository.Tmc4uBatchRepository;
import com.pleyfair.mipmip.repository.Tmc4uPersonRepository;
import com.pleyfair.mipmip.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class Tmc4uService {
    private final Tmc4uPersonRepository tmc4uPersonRepository;
    private final Tmc4uBatchRepository tmc4uBatchRepository;
    private final TransactionRepository transactionRepository;

    public Tmc4uService(Tmc4uPersonRepository tmc4uPersonRepository, Tmc4uBatchRepository tmc4uBatchRepository, TransactionRepository transactionRepository) {
        this.tmc4uPersonRepository = tmc4uPersonRepository;
        this.tmc4uBatchRepository = tmc4uBatchRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * stores a CSV multipartFile in the corresponding tables uploaded_person and uploaded_csv_file
     * @param tmc4uPersonList a list of JSON leads
     * @return the Tmc4uBatch that was saved
     */
    public void processBatch(List<Tmc4uPerson> tmc4uPersonList)  {
        if (tmc4uPersonList == null)
            throw new IllegalArgumentException("Tmc4uPersonList is null");
         if( tmc4uPersonList.isEmpty())
             throw new EmptyListException("Tmc4uPersonList is empty", HttpStatus.BAD_REQUEST);
        Tmc4uBatch tmc4uBatch = Tmc4uBatch.builder()
                .dateTime(new Date().toString())
                .personCount(tmc4uPersonList.size())
                .build();
        tmc4uBatch = tmc4uBatchRepository.save(tmc4uBatch);
        for (Tmc4uPerson tmc4uPerson : tmc4uPersonList) {
            tmc4uPerson.setBatchId(tmc4uBatch.getId());
        }
        tmc4uPersonRepository.saveAll(tmc4uPersonList);

        Transaction transaction = Transaction
                .builder()
                .batchId(tmc4uBatch.getId())
                .status(Status.TO_PROCESS)
                .dateTimeCreated(String.valueOf(Date.from(Instant.now())))
                .build();
        transactionRepository.save(transaction);
    }
}