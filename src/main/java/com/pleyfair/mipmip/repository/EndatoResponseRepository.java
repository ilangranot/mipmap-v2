package com.pleyfair.mipmip.repository;


import com.pleyfair.mipmip.model.dto.endato.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndatoResponseRepository extends JpaRepository<Response, Long> {

}