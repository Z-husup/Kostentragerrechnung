package com.prog.kostentragerrechnung.tables;


import javafx.beans.property.*;

public class WorkPlanTable {
    private final StringProperty workPlanId = new SimpleStringProperty();
    private final IntegerProperty stepNumber = new SimpleIntegerProperty();
    private final StringProperty operationDescription = new SimpleStringProperty();
    private final DoubleProperty duration = new SimpleDoubleProperty();
    private final StringProperty machineId = new SimpleStringProperty();
    private final StringProperty workStationId = new SimpleStringProperty();

    public String getWorkPlanId() { return workPlanId.get(); }
    public void setWorkPlanId(String value) { workPlanId.set(value); }
    public StringProperty workPlanIdProperty() { return workPlanId; }

    public int getStepNumber() { return stepNumber.get(); }
    public void setStepNumber(int value) { stepNumber.set(value); }
    public IntegerProperty stepNumberProperty() { return stepNumber; }

    public String getOperationDescription() { return operationDescription.get(); }
    public void setOperationDescription(String value) { operationDescription.set(value); }
    public StringProperty operationDescriptionProperty() { return operationDescription; }

    public double getDuration() { return duration.get(); }
    public void setDuration(double value) { duration.set(value); }
    public DoubleProperty durationProperty() { return duration; }

    public String getMachineId() { return machineId.get(); }
    public void setMachineId(String value) { machineId.set(value); }
    public StringProperty machineIdProperty() { return machineId; }

    public String getWorkStationId() { return workStationId.get(); }
    public void setWorkStationId(String value) { workStationId.set(value); }
    public StringProperty workStationIdProperty() { return workStationId; }
}

