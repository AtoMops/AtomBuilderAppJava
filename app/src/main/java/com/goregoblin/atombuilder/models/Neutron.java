package com.goregoblin.atombuilder.models;

public class Neutron extends Nucleon {

    public Neutron() {
        // hier Werte für Neutron (können und sollen nicht geändert werden)

        setName("Neutron");
        setMassKG(1.674_927_498_04 * Math.pow(10, -27));
        setMassU(1.008_664_915_95);
        setRestEnergy(939.565_420_52);
        setElectricLoad(0);

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
