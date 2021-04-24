package com.gfg.modelsClass;

import com.gfg.IFly;

import java.util.Date;

public class Aeroplane implements IFly {
    private String serviceProvider;
    private Date takeOfTime;
    private Integer numberOfSeats;

    public Aeroplane() {
    }

    public Aeroplane(String serviceProvider, Date takeOfTime, Integer numberOfSeats) {
        this.serviceProvider = serviceProvider;
        this.takeOfTime = takeOfTime;
        this.numberOfSeats = numberOfSeats;
    }

    public String getServiceProvider(){
        return this.serviceProvider;
    }
    public Date getTakeOfTime(){
        return this.takeOfTime;
    }

    public Integer getNumberOfSeats(){
        return this.numberOfSeats;
    }

    @Override
    public void fly() {
        System.out.println("Aeroplane flies 300 km per hour");
    }
}
