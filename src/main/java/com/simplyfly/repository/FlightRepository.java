package com.simplyfly.repository;

import com.simplyfly.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

	List<Flight> findByOriginAndDestinationAndDepartureTimeContains(String origin, String destination, String date);

	boolean existsById(Long id);
}
