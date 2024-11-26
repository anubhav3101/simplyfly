package com.simplyfly.repository;

import com.simplyfly.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {

    Route findByOriginAndDestination(String origin, String destination);
}
