package com.goregoblin.atombuilder.models;

public abstract class Nucleon {

    String name;
    double massKG; // Masse in KG
    double massU;  // Masse in atomarer Masseneinheit u
    double restEnergy; // // RuheEnergie (in MeV)
    int electricLoad; //  elektrische Ladung in eV; z.B. ein Elektron hat -1e was -1,602_176_634 · 10−19 C entspricht; ei


    public Nucleon() {

    }


    void setName(String name) {
        this.name = name;
    }

    void setMassKG(double massKG) {
        this.massKG = massKG;
    }

    void setMassU(double massU) {
        this.massU = massU;
    }

    void setRestEnergy(double restEnergy) {
        this.restEnergy = restEnergy;
    }

    void setElectricLoad(int electricLoad) {
        this.electricLoad = electricLoad;
    }

    public String getName() {
        return name;
    }

    public double getMassKG() {
        return massKG;
    }

    public double getMassU() {
        return massU;
    }

    public double getRestEnergy() {
        return restEnergy;
    }

    public int getElectricLoad() {
        return electricLoad;
    }
}


