package com.fleetmanagement.repository;

import com.fleetmanagement.model.DeliveryPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPointRepository extends JpaRepository<DeliveryPoint,Integer> {

}
