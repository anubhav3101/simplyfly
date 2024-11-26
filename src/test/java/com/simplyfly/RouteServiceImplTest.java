package com.simplyfly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.simplyfly.exceptions.RouteNotFoundException;
import com.simplyfly.model.Route;
import com.simplyfly.repository.RouteRepository;
import com.simplyfly.services.RouteServiceImpl;

class RouteServiceImplTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteServiceImpl routeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRoute() {
        // Arrange
        Route newRoute = new Route();
        newRoute.setOrigin("New York");
        newRoute.setDestination("Los Angeles");

        when(routeRepository.save(newRoute)).thenReturn(newRoute);

        // Act
        Route createdRoute = routeService.createRoute(newRoute);

        // Assert
        assertEquals("New York", createdRoute.getOrigin());
        assertEquals("Los Angeles", createdRoute.getDestination());
        verify(routeRepository, times(1)).save(newRoute);
    }

    @Test
    void testFindRouteById_Success() {
        // Arrange
        Long routeId = 1L;
        Route route = new Route();
        route.setId(routeId);

        when(routeRepository.findById(routeId)).thenReturn(Optional.of(route));

        // Act
        Route foundRoute = routeService.findRouteById(routeId);

        // Assert
        assertNotNull(foundRoute);
        assertEquals(routeId, foundRoute.getId());
        verify(routeRepository, times(1)).findById(routeId);
    }

    @Test
    void testFindRouteById_Failure() {
        // Arrange
        Long routeId = 1L;

        when(routeRepository.findById(routeId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RouteNotFoundException.class, () -> {
            routeService.findRouteById(routeId);
        });
        assertEquals("Route not found with ID: 1", exception.getMessage());
        verify(routeRepository, times(1)).findById(routeId);
    }

    @Test
    void testFindByOriginAndDestination_Success() {
        // Arrange
        String origin = "New York";
        String destination = "Los Angeles";
        Route route = new Route();
        route.setOrigin(origin);
        route.setDestination(destination);

        when(routeRepository.findByOriginAndDestination(origin, destination)).thenReturn(route);

        // Act
        Route foundRoute = routeService.findByOriginAndDestination(origin, destination);

        // Assert
        assertNotNull(foundRoute);
        assertEquals(origin, foundRoute.getOrigin());
        assertEquals(destination, foundRoute.getDestination());
        verify(routeRepository, times(1)).findByOriginAndDestination(origin, destination);
    }

    @Test
    void testFindByOriginAndDestination_Failure() {
        // Arrange
        String origin = "New York";
        String destination = "San Francisco";

        when(routeRepository.findByOriginAndDestination(origin, destination)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(RouteNotFoundException.class, () -> {
            routeService.findByOriginAndDestination(origin, destination);
        });
        assertEquals("Route not found for origin: New York and destination: San Francisco", exception.getMessage());
        verify(routeRepository, times(1)).findByOriginAndDestination(origin, destination);
    }

    @Test
    void testGetAllRoutes() {
        // Arrange
        Route route1 = new Route();
        route1.setOrigin("New York");
        route1.setDestination("Los Angeles");

        Route route2 = new Route();
        route2.setOrigin("Chicago");
        route2.setDestination("Houston");

        List<Route> routes = Arrays.asList(route1, route2);

        when(routeRepository.findAll()).thenReturn(routes);

        // Act
        List<Route> allRoutes = routeService.getAllRoutes();

        // Assert
        assertEquals(2, allRoutes.size());
        verify(routeRepository, times(1)).findAll();
    }
}
