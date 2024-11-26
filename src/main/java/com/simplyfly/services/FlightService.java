package com.simplyfly.services;

import com.simplyfly.model.Flight;

import java.util.List;

public interface FlightService {
    List<Flight> searchFlights(String origin, String destination, String date);
    Flight addFlight(Flight flight);
    void deleteFlight(Long flightId);
}
