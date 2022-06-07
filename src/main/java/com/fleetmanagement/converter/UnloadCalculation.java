package com.fleetmanagement.converter;

import com.fleetmanagement.constant.DeliveryPointType;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.shipment.Bag;
import com.fleetmanagement.model.shipment.Package;

public interface UnloadCalculation {

    ShipmentUnloadCalculation returnCalculationMethod();

    interface ShipmentUnloadCalculation {
        int calculateUnloading(DeliveryPoint deliveryPoint);
    }

    class BagUnloadingUnloadCalculation implements ShipmentUnloadCalculation {
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
            boolean allPacksUnloaded = bag.getPackages().stream().allMatch(aPackage -> aPackage.getStatus() == 4);
            return Boolean.TRUE == allPacksUnloaded ? 4 : 3;
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

    class PackageAssignedBagUnloadingUnloadCalculation implements ShipmentUnloadCalculation {

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
    }


}
