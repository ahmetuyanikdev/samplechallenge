package com.fleetmanagement.repository;

import com.fleetmanagement.model.IncorrectDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncorrectDeliveryRepository extends JpaRepository<IncorrectDelivery, Integer> {
    List<IncorrectDelivery> findAllByDeliveryPointId(int id);
}
