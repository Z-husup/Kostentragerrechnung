package com.prog.kostentragerrechnung.tables;

import javafx.beans.property.*;

public class PartTable {
    private final StringProperty partId = new SimpleStringProperty();
    private final StringProperty materialId = new SimpleStringProperty();
    private final StringProperty partName = new SimpleStringProperty();
    private final DoubleProperty weight = new SimpleDoubleProperty();
    private final StringProperty dimensions = new SimpleStringProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();

    public String getPartId() { return partId.get(); }
    public void setPartId(String value) { partId.set(value); }
    public StringProperty partIdProperty() { return partId; }

    public String getMaterialId() { return materialId.get(); }
    public void setMaterialId(String value) { materialId.set(value); }
    public StringProperty materialIdProperty() { return materialId; }

    public String getPartName() { return partName.get(); }
    public void setPartName(String value) { partName.set(value); }
    public StringProperty partNameProperty() { return partName; }

    public double getWeight() { return weight.get(); }
    public void setWeight(double value) { weight.set(value); }
    public DoubleProperty weightProperty() { return weight; }

    public String getDimensions() { return dimensions.get(); }
    public void setDimensions(String value) { dimensions.set(value); }
    public StringProperty dimensionsProperty() { return dimensions; }

    public int getQuantity() { return quantity.get(); }
    public void setQuantity(int value) { quantity.set(value); }
    public IntegerProperty quantityProperty() { return quantity; }
}