package com.prog.kostentragerrechnung.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProductStructureTable {
    private final StringProperty productId = new SimpleStringProperty();
    private final StringProperty componentId = new SimpleStringProperty();
    private final StringProperty componentName = new SimpleStringProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();
    private final StringProperty geometry = new SimpleStringProperty();
    private final StringProperty teilId = new SimpleStringProperty();

    public String getProductId() { return productId.get(); }
    public void setProductId(String value) { productId.set(value); }
    public StringProperty productIdProperty() { return productId; }

    public String getComponentId() { return componentId.get(); }
    public void setComponentId(String value) { componentId.set(value); }
    public StringProperty componentIdProperty() { return componentId; }

    public String getComponentName() { return componentName.get(); }
    public void setComponentName(String value) { componentName.set(value); }
    public StringProperty componentNameProperty() { return componentName; }

    public int getQuantity() { return quantity.get(); }
    public void setQuantity(int value) { quantity.set(value); }
    public IntegerProperty quantityProperty() { return quantity; }

    public String getGeometry() { return geometry.get(); }
    public void setGeometry(String value) { geometry.set(value); }
    public StringProperty geometryProperty() { return geometry; }

    public String getTeilId() { return teilId.get(); }
    public void setTeilId(String value) { teilId.set(value); }
    public StringProperty teilIdProperty() { return teilId; }
}

