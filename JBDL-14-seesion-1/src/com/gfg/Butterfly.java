package com.gfg;


public class Butterfly extends FlyingSpecies implements IFly{
    public Butterfly(Integer numberOfWings, Integer weight) {
        super(numberOfWings, weight);
    }

    @Override
    public void fly() {
        System.out.println("Butterfly flies 3km per hour");
    }
}
