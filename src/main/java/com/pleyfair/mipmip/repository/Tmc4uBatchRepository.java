package com.pleyfair.mipmip.repository;

import com.pleyfair.mipmip.model.dto.request.Tmc4uBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Tmc4uBatchRepository extends JpaRepository<Tmc4uBatch, Long> {

}