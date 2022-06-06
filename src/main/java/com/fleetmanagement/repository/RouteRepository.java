package com.fleetmanagement.repository;

import com.fleetmanagement.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route,Integer> {
    List<Route> findAllByVehiclePlateNumber(String plateNumber);
    void deleteByVehicle_plateNumber(String plateNumber);
}
