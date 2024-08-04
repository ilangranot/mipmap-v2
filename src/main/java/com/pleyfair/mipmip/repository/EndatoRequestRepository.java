package com.pleyfair.mipmip.repository;


import com.pleyfair.mipmip.model.dto.endato.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndatoRequestRepository extends JpaRepository<Request, Long> {

}