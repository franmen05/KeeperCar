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
public class UserVehSimple {

    private String userId;
    private int vehicleId;
    

    public UserVehSimple() {
    }

    public UserVehSimple(String userId, int vehicleId) {
        setUserId(userId);
        setVehicleId(vehicleId);
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
  
    
}
