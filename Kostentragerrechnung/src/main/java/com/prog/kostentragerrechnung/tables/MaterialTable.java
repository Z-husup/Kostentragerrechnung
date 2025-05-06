package com.prog.kostentragerrechnung.tables;

import javafx.beans.property.*;

public class MaterialTable {
    private final StringProperty materialId = new SimpleStringProperty();
    private final StringProperty materialName = new SimpleStringProperty();
    private final DoubleProperty unitCost = new SimpleDoubleProperty();
    private final StringProperty unit = new SimpleStringProperty();
    private final IntegerProperty stockQuantity = new SimpleIntegerProperty();

    public MaterialTable() {}

    public String getMaterialId() { return materialId.get(); }
    public void setMaterialId(String value) { materialId.set(value); }
    public StringProperty materialIdProperty() { return materialId; }

    public String getMaterialName() { return materialName.get(); }
    public void setMaterialName(String value) { materialName.set(value); }
    public StringProperty materialNameProperty() { return materialName; }

    public double getUnitCost() { return unitCost.get(); }
    public void setUnitCost(double value) { unitCost.set(value); }
    public DoubleProperty unitCostProperty() { return unitCost; }

    public String getUnit() { return unit.get(); }
    public void setUnit(String value) { unit.set(value); }
    public StringProperty unitProperty() { return unit; }

    public int getStockQuantity() { return stockQuantity.get(); }
    public void setStockQuantity(int value) { stockQuantity.set(value); }
    public IntegerProperty stockQuantityProperty() { return stockQuantity; }
}
