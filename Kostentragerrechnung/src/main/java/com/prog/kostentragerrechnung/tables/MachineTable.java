package com.prog.kostentragerrechnung.tables;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MachineTable {
    private final StringProperty machineId = new SimpleStringProperty();
    private final StringProperty machineName = new SimpleStringProperty();
    private final DoubleProperty machineRate = new SimpleDoubleProperty();
    private final DoubleProperty workerRate = new SimpleDoubleProperty();

    public String getMachineId() { return machineId.get(); }
    public void setMachineId(String value) { machineId.set(value); }
    public StringProperty machineIdProperty() { return machineId; }

    public String getMachineName() { return machineName.get(); }
    public void setMachineName(String value) { machineName.set(value); }
    public StringProperty machineNameProperty() { return machineName; }

    public double getMachineRate() { return machineRate.get(); }
    public void setMachineRate(double value) { machineRate.set(value); }
    public DoubleProperty machineRateProperty() { return machineRate; }

    public double getWorkerRate() { return workerRate.get(); }
    public void setWorkerRate(double value) { workerRate.set(value); }
    public DoubleProperty workerRateProperty() { return workerRate; }
}
