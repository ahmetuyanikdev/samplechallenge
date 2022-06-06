package com.fleetmanagement.model;

import com.fleetmanagement.model.shipment.Shipment;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int id;

    @ManyToOne
    @JoinColumn(name = "deliveryPoint_id",referencedColumnName = "id")
    private DeliveryPoint deliveryPoint;

    @OneToMany(mappedBy = "route")
    private Set<Shipment> deliveries;

    @ManyToOne
    @JoinColumn(name = "vehicle_plateNumber",referencedColumnName = "plateNumber")
    private Vehicle vehicle;

    public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

    public static Route getInstance(){
        return new Route();
    }

    public int getId() {
        return id;
    }

    public DeliveryPoint getDeliveryPoint() {
        return deliveryPoint;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Set<Shipment> getDeliveries() {
        return deliveries;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDeliveries(Set<Shipment> deliveries) {
        this.deliveries = deliveries;
    }
}
