package com.simplyfly.mapper;

import com.simplyfly.dto.FlightDTO;
import com.simplyfly.model.Flight;
import com.simplyfly.model.Route;

import java.util.List;
import java.util.stream.Collectors;

public class FlightMapper {

    // Convert Flight entity to FlightDTO
    public static FlightDTO toDTO(Flight flight) {
        return new FlightDTO(
                flight.getId(),
                flight.getFlightName(),
                flight.getFlightNumber(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getSeatsAvailable(),
                flight.getFare(),
                flight.getRoute() != null ? flight.getRoute().getId() : null // set routeId from Route entity
        );
    }

    // Convert List of Flight entities to List of FlightDTOs
    public static List<FlightDTO> toDTOList(List<Flight> flights) {
        return flights.stream().map(FlightMapper::toDTO).collect(Collectors.toList());
    }

    // Convert FlightDTO to Flight entity (optional, if needed)
    public static Flight toEntity(FlightDTO flightDTO) {
        Flight flight = new Flight();
        flight.setId(flightDTO.getId());
        flight.setFlightName(flightDTO.getFlightName());
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setOrigin(flightDTO.getOrigin());
        flight.setDestination(flightDTO.getDestination());
        flight.setDepartureTime(flightDTO.getDepartureTime());
        flight.setArrivalTime(flightDTO.getArrivalTime());
        flight.setSeatsAvailable(flightDTO.getSeatsAvailable());
        flight.setFare(flightDTO.getFare());

        // Link route using routeId from the DTO
        if (flightDTO.getRouteId() != null) {
            Route route = new Route();
            route.setId(flightDTO.getRouteId());  // Set the routeId in Route entity
            flight.setRoute(route);
        }

        return flight;
    }
}
