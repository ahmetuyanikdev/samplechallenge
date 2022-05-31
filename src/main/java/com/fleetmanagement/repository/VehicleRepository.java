package com.fleetmanagement.repository;

import com.fleetmanagement.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle,String> {

}
