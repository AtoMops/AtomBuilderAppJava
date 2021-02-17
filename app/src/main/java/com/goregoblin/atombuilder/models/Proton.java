package com.goregoblin.atombuilder.models;

public class Proton extends Nucleon {

    public Proton() {
        // hier Werte für Proton

        setName("Proton");
        setMassKG(1.672_621_923_69 * Math.pow(10,-27));
        setMassU(1.007_276_466_583);
        setRestEnergy(938.272_088_16);
        setElectricLoad(1);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getMassKG() {
        return super.getMassKG();
    }

    @Override
    public double getMassU() {
        return super.getMassU();
    }

    @Override
    public double getRestEnergy() {
        return super.getRestEnergy();
    }

    @Override
    public int getElectricLoad() {
        return super.getElectricLoad();
    }

    @Override
    void setName(String name) {
        super.setName(name);
    }

    @Override
    void setMassKG(double massKG) {
        super.setMassKG(massKG);
    }

    @Override
    void setMassU(double massU) {
        super.setMassU(massU);
    }

    @Override
    void setRestEnergy(double restEnergy) {
        super.setRestEnergy(restEnergy);
    }

    @Override
    void setElectricLoad(int electricLoad) {
        super.setElectricLoad(electricLoad);
    }
}
