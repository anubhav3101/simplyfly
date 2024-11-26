package com.simplyfly.mapper;

import com.simplyfly.dto.RouteDTO;
import com.simplyfly.model.Route;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RouteMapper {

    public RouteDTO toDTO(Route route) {
        return new RouteDTO(route.getId(), route.getOrigin(), route.getDestination());
    }

    public List<RouteDTO> toDTOList(List<Route> routes) {
        return routes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
