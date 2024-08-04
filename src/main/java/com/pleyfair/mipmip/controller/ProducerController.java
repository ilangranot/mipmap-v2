package com.pleyfair.mipmip.controller;

import com.pleyfair.mipmip.model.dto.request.Tmc4uPerson;
import com.pleyfair.mipmip.service.Tmc4uService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller
 */
@Validated
@RestController
@RequestMapping("/api/v1")
@Tag(name = "PleyFair-Router")
@Slf4j
public class ProducerController {

    private final Tmc4uService tmc4uService;

    public ProducerController(Tmc4uService tmc4uService) {
        this.tmc4uService = tmc4uService;
    }

    /**
     * /tmc4u Endpoint
     * @param tmc4uPersonList a JSON POST according to Tmc4UPerson Entity
     * @return HTTP OK or exceptions
     */
    @PostMapping("/tmc4u")
    @Operation(summary = "Allows Tmc4U to post to us fresh leads")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success uploading leads,", content = @Content),
            @ApiResponse(responseCode = "400", description = "Some bad request", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<String> newTmc4uLeads(
            @NotEmpty(message = "Input lead list cannot be empty.")
            @RequestBody List<@Valid Tmc4uPerson> tmc4uPersonList
    ) {
        log.info("Received new Tmc4u leads: {}", tmc4uPersonList);
        tmc4uService.processBatch(tmc4uPersonList);

        return ResponseEntity.ok("Leads accepted. Everything is okay!");
    }
}