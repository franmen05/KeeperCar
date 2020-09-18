/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guille.keepercar.data.model.json;

/**
 *
 * @author Guille
 */
public class MaintenanceTypeSimple {

        private int maintenanceTypeId;

        private String name;

        private long distance;

        public MaintenanceTypeSimple() {
        }

        public MaintenanceTypeSimple(int maintenanceTypeId) {
            this.maintenanceTypeId = maintenanceTypeId;
        }

        public int getMaintenanceTypeId() {
            return maintenanceTypeId;
        }

        public void setMaintenanceTypeId(Integer maintenanceTypeId) {
            this.maintenanceTypeId = maintenanceTypeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getDistance() {
            return distance;
        }

        public void setDistance(long distance) {
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "com.manveh.data.model.Maintenancetype[ maintenanceTypeId=" + maintenanceTypeId + " ]";
        }
    }

    