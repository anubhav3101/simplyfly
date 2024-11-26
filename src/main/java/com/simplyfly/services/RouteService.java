package com.simplyfly.services;

import com.simplyfly.dto.RouteDTO;
import com.simplyfly.model.Route;
import java.util.List;

public interface RouteService {
    Route createRoute(Route route);
    Route findRouteById(Long id);
    List<Route> getAllRoutes();
    Route findByOriginAndDestination(String origin, String destination);
}
