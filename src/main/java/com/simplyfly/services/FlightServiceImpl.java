package com.simplyfly.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplyfly.exceptions.BadRequestException;
import com.simplyfly.exceptions.ResourceNotFoundException;
import com.simplyfly.model.Flight;
import com.simplyfly.model.Route;
import com.simplyfly.repository.FlightRepository;
import com.simplyfly.repository.RouteRepository;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private RouteRepository routeRepository; // Autowired RouteRepository

    @Override
    public List<Flight> searchFlights(String origin, String destination, String date) {
        return flightRepository.findByOriginAndDestinationAndDepartureTimeContains(origin, destination, date);
    }

    @Override
    public Flight addFlight(Flight flight) {
    	if (flight.getRoute() == null || flight.getRoute().getId() == null) {
    	    throw new BadRequestException("Route ID is required.");
    	}


        // Debugging the route ID
        System.out.println("Route ID: " + flight.getRoute().getId());

        // Fetch and validate the route
        Route route = routeRepository.findById(flight.getRoute().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with ID: " + flight.getRoute().getId()));
        flight.setRoute(route);

        return flightRepository.save(flight);
    }

    @Override
    public void deleteFlight(Long flightId) {
        if (!flightRepository.existsById(flightId)) {
            throw new ResourceNotFoundException("Flight not found with ID: " + flightId);
        }
        flightRepository.deleteById(flightId);
    }
}
