package com.fleetmanagement.converter;

import com.fleetmanagement.constant.DeliveryPointType;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.shipment.Bag;
import com.fleetmanagement.model.shipment.Package;

public interface UnloadCalculation {

    ShipmentUnloadCalculation returnCalculationMethod();

    PostUpdateShipmentUnloadCalculation returnPostUpdateCalculationMethod();

    interface ShipmentUnloadCalculation {
        int calculateUnloading(DeliveryPoint deliveryPoint);
    }

    interface PostUpdateShipmentUnloadCalculation {
        int calculatePostUpdateUnloading(DeliveryPoint deliveryPoint);
    }

    class BagUnloadingUnloadCalculation implements ShipmentUnloadCalculation, PostUpdateShipmentUnloadCalculation {
        Bag bag;

        public BagUnloadingUnloadCalculation(Bag bag) {
            this.bag = bag;
        }

        @Override
        public int calculateUnloading(DeliveryPoint deliveryPoint) {
            if (bag.getDeliveryPoint().equals(deliveryPoint) && (deliveryPoint.getType().equals(DeliveryPointType.Distribution_Center.name()) ||
                    deliveryPoint.getType().equals(DeliveryPointType.Transfer_Center.name()))) {
                return 4;
            }
            return 3;
        }

        @Override
        public int calculatePostUpdateUnloading(DeliveryPoint deliveryPoint) {
            Boolean bagStatus = this.bag.getPackages().stream().allMatch(pack -> pack.getStatus() == 4) &&
                    this.bag.getDeliveryPoint().equals(deliveryPoint);
            return Boolean.TRUE.equals(bagStatus) ? 4 : bag.getStatus();
        }
    }

    class PackageUnloadingUnloadCalculation implements ShipmentUnloadCalculation {

        Package pack;

        public PackageUnloadingUnloadCalculation(Package pack) {
            this.pack = pack;
        }

        @Override
        public int calculateUnloading(DeliveryPoint deliveryPoint) {
            if (pack.getDeliveryPoint().equals(deliveryPoint) && (deliveryPoint.getType().equals(DeliveryPointType.Branch.name()) ||
                    deliveryPoint.getType().equals(DeliveryPointType.Distribution_Center.name()))) {
                return 4;
            }
            return 3;
        }
    }

    class PackageAssignedBagUnloadingUnloadCalculation implements ShipmentUnloadCalculation, PostUpdateShipmentUnloadCalculation {

        Package pack;

        public PackageAssignedBagUnloadingUnloadCalculation(Package pack) {
            this.pack = pack;
        }

        @Override
        public int calculateUnloading(DeliveryPoint deliveryPoint) {
            if (pack.getDeliveryPoint().equals(deliveryPoint) && (deliveryPoint.getType().equals(DeliveryPointType.Distribution_Center.name()) ||
                    deliveryPoint.getType().equals(DeliveryPointType.Transfer_Center.name()))) {
                return 4;
            }
            return 3;
        }

        @Override
        public int calculatePostUpdateUnloading(DeliveryPoint deliveryPoint) {
            boolean bagStatus = pack.getBag().getStatus() == 4 && pack.getDeliveryPoint().equals(deliveryPoint);
            return Boolean.TRUE.equals(bagStatus) ? 4 : this.pack.getStatus();
        }
    }


}
