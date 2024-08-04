package com.pleyfair.mipmip.repository;

import com.pleyfair.mipmip.model.dto.request.Tmc4uPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Tmc4uPersonRepository extends JpaRepository<Tmc4uPerson, Long> {
    List<Tmc4uPerson> findByBatchId(long batchId);
}