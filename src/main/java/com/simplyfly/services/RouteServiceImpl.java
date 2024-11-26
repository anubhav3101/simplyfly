package com.simplyfly.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplyfly.dto.RouteDTO;
import com.simplyfly.exceptions.RouteNotFoundException;
import com.simplyfly.mapper.RouteMapper;
import com.simplyfly.model.Route;
import com.simplyfly.repository.RouteRepository;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RouteMapper routeMapper;

    @Override
    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

    @Override
    public Route findRouteById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new RouteNotFoundException("Route not found with ID: " + id));
    }

    @Override
    public Route findByOriginAndDestination(String origin, String destination) {
        Route route = routeRepository.findByOriginAndDestination(origin, destination);
        if (route == null) {
            throw new RouteNotFoundException("Route not found for origin: " + origin + " and destination: " + destination);
        }
        return route;
    }

    @Override
    public List<Route> getAllRoutes() {
        return routeRepository.findAll(); // Fix to return all routes
    }
}
