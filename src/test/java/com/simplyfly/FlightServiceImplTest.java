package com.simplyfly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.simplyfly.exceptions.BadRequestException;
import com.simplyfly.exceptions.ResourceNotFoundException;
import com.simplyfly.model.Flight;
import com.simplyfly.model.Route;
import com.simplyfly.repository.FlightRepository;
import com.simplyfly.repository.RouteRepository;
import com.simplyfly.services.FlightServiceImpl;

class FlightServiceImplTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private FlightServiceImpl flightService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchFlights_Success() {
        // Arrange
        String origin = "New York";
        String destination = "Los Angeles";
        String date = "2024-12-01";

        Flight flight1 = new Flight();
        flight1.setId(1L);
        flight1.setOrigin(origin);
        flight1.setDestination(destination);

        List<Flight> flights = Arrays.asList(flight1);
        when(flightRepository.findByOriginAndDestinationAndDepartureTimeContains(origin, destination, date))
                .thenReturn(flights);

        // Act
        List<Flight> result = flightService.searchFlights(origin, destination, date);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(flightRepository, times(1))
                .findByOriginAndDestinationAndDepartureTimeContains(origin, destination, date);
    }

    @Test
    void testAddFlight_Success() {
        // Arrange
        Route route = new Route();
        route.setId(1L);

        Flight flight = new Flight();
        flight.setRoute(route);

        when(routeRepository.findById(route.getId())).thenReturn(Optional.of(route));
        when(flightRepository.save(flight)).thenReturn(flight);

        // Act
        Flight savedFlight = flightService.addFlight(flight);

        // Assert
        assertNotNull(savedFlight);
        assertEquals(route, savedFlight.getRoute());
        verify(routeRepository, times(1)).findById(route.getId());
        verify(flightRepository, times(1)).save(flight);
    }

    @Test
    void testAddFlight_Failure_RouteNotFound() {
        // Arrange
        Route route = new Route();
        route.setId(1L);

        Flight flight = new Flight();
        flight.setRoute(route);

        when(routeRepository.findById(route.getId())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            flightService.addFlight(flight);
        });
        assertEquals("Route not found with ID: 1", exception.getMessage());
        verify(routeRepository, times(1)).findById(route.getId());
        verify(flightRepository, never()).save(flight);
    }

  


    @Test
    void testDeleteFlight_Success() {
        // Arrange
        Long flightId = 1L;
        when(flightRepository.existsById(flightId)).thenReturn(true);

        // Act
        flightService.deleteFlight(flightId);

        // Assert
        verify(flightRepository, times(1)).existsById(flightId);
        verify(flightRepository, times(1)).deleteById(flightId);
    }

    @Test
    void testDeleteFlight_Failure_NotFound() {
        // Arrange
        Long flightId = 1L;
        when(flightRepository.existsById(flightId)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            flightService.deleteFlight(flightId);
        });
        assertEquals("Flight not found with ID: 1", exception.getMessage());
        verify(flightRepository, times(1)).existsById(flightId);
        verify(flightRepository, never()).deleteById(flightId);
    }
}
