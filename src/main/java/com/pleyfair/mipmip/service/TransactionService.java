package com.pleyfair.mipmip.service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pleyfair.mipmip.client.EndatoClient;
import com.pleyfair.mipmip.helper.LinkingAndLearningHelper;
import com.pleyfair.mipmip.model.dto.endato.Request;
import com.pleyfair.mipmip.model.dto.endato.Response;
import com.pleyfair.mipmip.model.dto.process.Transaction;
import com.pleyfair.mipmip.model.dto.request.Tmc4uBatch;
import com.pleyfair.mipmip.model.dto.request.Tmc4uPerson;
import com.pleyfair.mipmip.model.enums.Status;
import com.pleyfair.mipmip.repository.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.Entity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Deals with processing the requests from Endato in bulks
 */
@Slf4j
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final Tmc4uBatchRepository tmc4uBatchRepository;
    private final Tmc4uPersonRepository tmc4uPersonRepository;
    private final EndatoResponseRepository endatoResponseRepository;
    private final EndatoRequestRepository endatoRequestRepository;
    private final EndatoClient endatoClient;

    public TransactionService(TransactionRepository transactionRepository,
                              EndatoResponseRepository endatoResponseRepository,
                              EndatoRequestRepository endatoRequestRepository,
                              Tmc4uBatchRepository tmc4uBatchRepository,
                              Tmc4uPersonRepository tmc4uPersonRepository, EndatoClient endatoClient) {
        this.endatoResponseRepository = endatoResponseRepository;
        this.endatoRequestRepository = endatoRequestRepository;
        this.tmc4uBatchRepository = tmc4uBatchRepository;
        this.tmc4uPersonRepository = tmc4uPersonRepository;
        this.transactionRepository = transactionRepository;
        this.endatoClient = endatoClient;
        log.info("TransactionService created");
    }

    /**
     * gets transactions with all statuses from the transaction repository
     * @return
     */
    public List getAllTransactions() {
        return transactionRepository.findAll();
    }

    /**
     * Processes transaction with a Status.TO_PROCESS
     * @return the transactions that were processed
     */
    @Transactional
    public List<Transaction> processTransactions(){
        // TO_PROCESS transactions list
        List<Transaction> toProcessTransactionsList = transactionRepository.findByStatus(Status.TO_PROCESS);
        // processed transaction list
        List<Transaction> processedTransactionsList = new LinkedList<>();
        // for each transaction TO_PROCESS
        for (Transaction transactionToProcess : toProcessTransactionsList) {
            List<Request> requestList = null;
                Tmc4uBatch tmc4uBatch = tmc4uBatchRepository.getReferenceById(transactionToProcess.getBatchId());
                requestList = getRequestsFromBatch(tmc4uBatch);
                tmc4uBatch.setStatus(Status.SUCCESS);
                tmc4uBatchRepository.save(tmc4uBatch);
                transactionToProcess.setSuccessfulRecords(tmc4uBatch.getPersonCount());
            // get RESPONSEs
            List<Response> responses = getResponsesFromRequests(requestList);
            // LINK, LEARN, AND SAVE to db
            linkAndLearnAndSaveResponses(responses);

            // set transaction as SUCCESS with date and count, and save to the db
            transactionToProcess.setStatus(Status.SUCCESS);
            transactionToProcess.setDateTimeSuccess(String.valueOf(Date.from(Instant.now())));
            transactionRepository.save(transactionToProcess);
            // add to the list of transactions processed
            processedTransactionsList.add(transactionToProcess);
        }
        // retain all data in CRM DB
//        insertPersonasToCrmDb();
        log.info("Processed transactions list");
        return processedTransactionsList;
    }


    // HELPER FUNCTIONS

    private void linkAndLearnAndSaveResponses(List<Response> responses) {
        // if the response has no error than do LINKING & LEARNING, and anyway SAVE response in db
        for (Response response : responses) {
            if (!response.isError)
                LinkingAndLearningHelper.processBiDirectionalLinkingAndLearningForResponse(response);
            try {
                endatoResponseRepository.save(response);
            } catch (Exception exception) {
                System.out.println("EXCEPTION IN LEARNING: " + exception.getMessage());
            }
        }
    }


    private List<Request> getRequestsFromBatch(Tmc4uBatch tmc4uBatch) {
        List<Request> requestList = new LinkedList<>();
        if (tmc4uBatch.getStatus() != Status.SUCCESS) {
            tmc4uBatch.setStatus(Status.IN_PROGRESS);
            List<Tmc4uPerson> tmc4uPersonList = tmc4uPersonRepository.findByBatchId(tmc4uBatch.getId());
            requestList.addAll(buildRequestListTmc4u(tmc4uPersonList));
        }
        return requestList;
    }



    private List<Request> buildRequestListTmc4u(List<Tmc4uPerson> tmc4uPersonList) {
        List<Request> requestList = new LinkedList<>();
        for (Tmc4uPerson tmc4uPerson : tmc4uPersonList) {
            Request request = new Request();
            request.setFirstName(tmc4uPerson.getFirstName());
            request.setLastName(tmc4uPerson.getLastName());
            String tempPhone = "";
            if (tmc4uPerson.getPhone() != null) {
                tempPhone = tmc4uPerson.getPhone();
            } else if (tmc4uPerson.getMobile() != null) {
                tempPhone = tmc4uPerson.getMobile();
            }
            String phoneNumber = tempPhone.replaceAll("[-+.^:,'()\\s]","");
            if (phoneNumber.length() > 10 && phoneNumber.startsWith("1"))
                phoneNumber = phoneNumber.substring(1, 11);
            request.setPhone(phoneNumber);

            request.setTmc4uPerson(tmc4uPerson);
            requestList.add(request);
        }
        return requestList;
    }

    private List<Response> getResponsesFromRequests(List<Request> requests) {
        List<Response> responses = new LinkedList<>();
        Response response;
        for (Request request : requests) {
            try {
                // TODO: RECURSIVE???? CONSIDER REMOVING ALL REQUEST.RESPONSES
                // a request can have missing response/s
                if (request.getResponses() == null)
                    request.setResponses(new ArrayList<>());
                // request person from Endato
                response = requestPersonFromEndato(request);
                request.getResponses().add(response);
                try {
                    endatoRequestRepository.save(request);
                } catch (DataIntegrityViolationException dataIntegrityViolationException){
                    System.out.println("EXCEPTION IN DAO (DataIntegrityViolationException): " + dataIntegrityViolationException.getMessage());
                } catch (JpaSystemException jpaSystemException){
                    System.out.println("EXCEPTION IN DAO (JpaSystemException): " + jpaSystemException.getMessage());
                } catch (Exception exception) {
                    System.out.println("EXCEPTION IN DAO (Exception): " + exception.getMessage());
                }
                response.setRequest(request);

                // add to responses list
                responses.add(response);
            } catch (URISyntaxException uriSyntaxException) {
                log.error(uriSyntaxException.getMessage());
            }
        }
        return responses;
    }

    private Response requestPersonFromEndato(Request request) throws URISyntaxException {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Entity payloadEntity = Entity.json(gson.toJson(request));
        String jsonResponseString = endatoClient.getJsonResponseString(payloadEntity);
        return gson.fromJson(jsonResponseString, Response.class);
    }
}