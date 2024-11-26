package com.simplyfly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simplyfly.dto.FlightDTO;
import com.simplyfly.exceptions.ResourceNotFoundException;
import com.simplyfly.model.Flight;
import com.simplyfly.model.Route;
import com.simplyfly.repository.RouteRepository;
import com.simplyfly.services.FlightService;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private RouteRepository routeRepository;

    // Add a new flight
    @PostMapping("/add")
    public ResponseEntity<FlightDTO> addFlight(@RequestBody FlightDTO flightDTO) {
        // Create a new flight entity
        Flight flight = new Flight();
        flight.setFlightName(flightDTO.getFlightName());
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setOrigin(flightDTO.getOrigin());
        flight.setDestination(flightDTO.getDestination());
        flight.setDepartureTime(flightDTO.getDepartureTime());
        flight.setArrivalTime(flightDTO.getArrivalTime());
        flight.setSeatsAvailable(flightDTO.getSeatsAvailable());
        flight.setFare(flightDTO.getFare());

        // Fetch the route using routeId from the flightDTO
        Route route = routeRepository.findById(flightDTO.getRouteId())
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with ID: " + flightDTO.getRouteId()));
        flight.setRoute(route);

        // Save the flight entity
        Flight savedFlight = flightService.addFlight(flight);

        // Convert the saved flight entity back to DTO to return as the response
        FlightDTO responseDTO = new FlightDTO(savedFlight.getId(), savedFlight.getFlightName(), savedFlight.getFlightNumber(),
                savedFlight.getOrigin(), savedFlight.getDestination(), savedFlight.getDepartureTime(), savedFlight.getArrivalTime(),
                savedFlight.getSeatsAvailable(), savedFlight.getFare(), savedFlight.getRoute().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
